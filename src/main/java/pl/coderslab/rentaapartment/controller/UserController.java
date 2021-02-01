package pl.coderslab.rentaapartment.controller;

import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.rentaapartment.model.Apartment;
import pl.coderslab.rentaapartment.model.Role;
import pl.coderslab.rentaapartment.model.User;
import pl.coderslab.rentaapartment.service.CountBillsService;
import pl.coderslab.rentaapartment.service.UserService;
import pl.coderslab.rentaapartment.validator.EditUserValidationGroup;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/app/user")
public class UserController {

    private final UserService userService;
    private final CountBillsService countBillsService;

    public UserController(UserService userService, CountBillsService countBillsService) {
        this.userService = userService;
        this.countBillsService = countBillsService;
    }

    @GetMapping("/details/{id}")
    public String userDetails(@PathVariable long id, Model model){
        Role user = userService.findById(id).map(User::getRole).orElseThrow();
        User optionalUser = userService.findById(id).orElse(new User());
        model.addAttribute("user",optionalUser);
        if(user.getAuthority().equals("ROLE_FIRM")){
            return "user/firmDetails";
        }
    return "user/userDetails";
    }


    @GetMapping("/auctions/{pageId}")
    public String myApartmentsAuctions(@PathVariable int pageId, Model model, Principal principal) throws NotFoundException {
        User user = userService.findByUserName(principal.getName()).orElseThrow(()->new NotFoundException("Nie znaleziono"));
        Pageable paging = PageRequest.of(pageId-1,10);
        Page<Apartment> activeApartments = userService.userActiveAuctions(user,paging);

        model.addAttribute("totalPages", activeApartments.getTotalPages());
        model.addAttribute("auctions", activeApartments.getContent());
        return "user/auctions";
    }

    @GetMapping("/rent-auctions/{pageId}")
    public String myApartmentsRented(@PathVariable int pageId, Model model, Principal principal) throws NotFoundException {
        User user = userService.findByUserName(principal.getName()).orElseThrow(()->new NotFoundException("Nie znaleziono"));
        Pageable paging = PageRequest.of(pageId-1,10);
        Page<Apartment> rentedApartments = userService.userRentedApartment(user, paging);
        model.addAttribute("totalPages", rentedApartments.getTotalPages());
        model.addAttribute("rentedApartment",rentedApartments.getContent());
        return "user/rentedBySomeone";
    }

    @GetMapping("/edit")
    public String editUser(Model model, Principal principal) throws NotFoundException {
        User byUserName = userService.findByUserName(principal.getName()).orElseThrow(()->new NotFoundException("Nie znaleziono"));
        model.addAttribute("user",userService.findById(byUserName.getId()));
        return "user/edit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute @Validated({EditUserValidationGroup.class}) User user, BindingResult result){
        if(result.hasErrors()){
            return "user/edit";
        }
        userService.saveUser(user);
        return "redirect:/app/1";
    }

    @GetMapping("/rented")
    public String rentedApartment(Principal principal, Model model) throws NotFoundException {
        User user = userService.findByUserName(principal.getName()).orElseThrow(()->new NotFoundException("Nie znaleziono"));
        model.addAttribute("apartment", user.getRentedHouse());
        if(user.getRentedHouse() == null){
        model.addAttribute("apartmentDoesntExist", false);
        }
        return "dashboard/myRentedApartment";
    }

    @GetMapping("/earning")
    public String countEarning(Model model, Principal principal) throws NotFoundException {
        User user = userService.findByUserName(principal.getName()).orElseThrow(()->new NotFoundException("Nie znaleziono"));
        List<Apartment> allApartmentsUser = userService.findApartmentsByUser(user);
        model.addAttribute("count", countBillsService.countBills(user));

        model.addAttribute("apartments",allApartmentsUser.size());

        model.addAttribute("rentedApartments", allApartmentsUser.stream().filter(x ->x.getTenantUser() != null).count());

        model.addAttribute("earningFromRentedApartment", countBillsService.countEarningFromRentedApartment(allApartmentsUser.stream()
        .filter(x->x.getTenantUser() != null).collect(Collectors.toList())));

        model.addAttribute("costsApartments",countBillsService.fullCostsUpKeepApartments(allApartmentsUser));

        model.addAttribute("costsUnrentedApartments", countBillsService.costsUnrentedApartments(allApartmentsUser) );
        return "user/earning";
    }
}
