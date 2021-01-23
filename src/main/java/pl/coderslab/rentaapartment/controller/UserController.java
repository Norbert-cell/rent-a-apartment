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
import pl.coderslab.rentaapartment.service.AddressService;
import pl.coderslab.rentaapartment.service.ApartmentService;
import pl.coderslab.rentaapartment.service.CountBillsService;
import pl.coderslab.rentaapartment.service.UserService;
import pl.coderslab.rentaapartment.validator.AddressValidationGroup;
import pl.coderslab.rentaapartment.validator.ApartmentValidationGroup;
import pl.coderslab.rentaapartment.validator.EditUserValidationGroup;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/app/user")
public class UserController {

    private final UserService userService;
    private final ApartmentService apartmentService;
    private final AddressService addressService;
    private final CountBillsService countBillsService;

    public UserController(UserService userService, ApartmentService apartmentService, AddressService addressService, CountBillsService countBillsService) {
        this.userService = userService;
        this.apartmentService = apartmentService;
        this.addressService = addressService;
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
        Page<Apartment> activeApartments = apartmentService.userActiveAuctions(user,paging);

        model.addAttribute("totalPages", activeApartments.getTotalPages());
        model.addAttribute("auctions", activeApartments.getContent());
        return "user/auctions";
    }

    @GetMapping("/rent-auctions/{pageId}")
    public String myApartmentsRented(@PathVariable int pageId, Model model, Principal principal) throws NotFoundException {
        User user = userService.findByUserName(principal.getName()).orElseThrow(()->new NotFoundException("Nie znaleziono"));
        Pageable paging = PageRequest.of(pageId-1,10);
        Page<Apartment> rentedApartments = apartmentService.userRentedApartment(user, paging);

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

    @GetMapping("/add-apartment")
    public String initaddApartment(Model model){
        model.addAttribute("apartment", new Apartment());
        return "apartment/add";
    }

    @PostMapping("/add-apartment")
    public String addApartment(@ModelAttribute @Validated({ApartmentValidationGroup.class, AddressValidationGroup.class}) Apartment apartment,
                                BindingResult result, Principal principal) throws NotFoundException {
        if (result.hasErrors()){
            return "apartment/add";
        }
        User owner = userService.findByUserName(principal.getName()).orElseThrow(()->new NotFoundException("Nie znaleziono"));
        apartment.setCreated(LocalDateTime.now());
        apartment.setRented(false);
        apartment.setOwnerUser(owner);
        addressService.saveAddress(apartment.getAddress());
        apartmentService.saveApartment(apartment);
        return "redirect:/app/1";
    }

    @GetMapping("/rented")
    public String rentedApartment(Principal principal, Model model) throws NotFoundException {
        User user = userService.findByUserName(principal.getName()).orElseThrow(()->new NotFoundException("Nie znaleziono"));
        model.addAttribute("apartment", user.getRentedHouse());
        return "dashboard/myRentedApartment";
    }

    @GetMapping("/earning")
    public String countEarning(Model model, Principal principal) throws NotFoundException {
        User user = userService.findByUserName(principal.getName()).orElseThrow(()->new NotFoundException("Nie znaleziono"));
        List<Apartment> allApartmentsUser = apartmentService.findApartmentByUser(user);
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
