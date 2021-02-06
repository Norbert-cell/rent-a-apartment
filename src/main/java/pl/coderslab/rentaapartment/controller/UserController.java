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
import pl.coderslab.rentaapartment.dto.UserDto;
import pl.coderslab.rentaapartment.dtoConverter.UserDtoConverter;
import pl.coderslab.rentaapartment.model.Apartment;
import pl.coderslab.rentaapartment.model.Role;
import pl.coderslab.rentaapartment.model.User;
import pl.coderslab.rentaapartment.service.CountBillsService;
import pl.coderslab.rentaapartment.service.UserService;
import pl.coderslab.rentaapartment.validator.EditFirmValidationGroup;
import pl.coderslab.rentaapartment.validator.EditUserValidationGroup;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/app/user")
public class UserController {

    private final UserService userService;
    private final CountBillsService countBillsService;
    private final UserDtoConverter userDtoConverter;


    public UserController(UserService userService, CountBillsService countBillsService, UserDtoConverter userDtoConverter) {
        this.userService = userService;
        this.countBillsService = countBillsService;
        this.userDtoConverter = userDtoConverter;
    }

    @GetMapping("/details/{id}")
    public String userDetails(@PathVariable long id, Model model){
        User optionalUser = userService.findById(id).orElse(null);
        if (optionalUser == null) {
            return "user/userDoesntExistError";
        }
        UserDto userDto = userDtoConverter.entityToDto(optionalUser);
        Role role = userDto.getRole();
        model.addAttribute("user",userDto);
        if(role.equals(Role.ROLE_FIRM)){
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
        User user = userService.findByUserName(principal.getName()).orElseThrow(()->new NotFoundException("Nie znaleziono"));
        model.addAttribute("user",user);
        if (user.getRole().equals(Role.ROLE_USER)){
            return "user/edit";
        }
        return "user/editFirm";
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute @Validated({EditUserValidationGroup.class}) User user, BindingResult result){
        if(result.hasErrors()){
            return "user/edit";
        }
        userService.saveUser(user);
        return "redirect:/app/1";
    }

    @PostMapping("/edit-firm")
    public String editFirm(@ModelAttribute @Validated({EditFirmValidationGroup.class}) User user, BindingResult result){
        if(result.hasErrors()){
            return "user/editFirm";
        }
        userService.saveUser(user);
        return "redirect:/app/1";
    }

    @GetMapping("/rented")
    public String rentedApartment(Principal principal, Model model) throws NotFoundException {
        User user = userService.findByUserName(principal.getName()).orElseThrow(()->new NotFoundException("Nie znaleziono"));
        if(user.getRentedHouse() == null){
        model.addAttribute("apartmentDoesntExist", true);
        }
        model.addAttribute("apartment", user.getRentedHouse());
        return "dashboard/myRentedApartment";
    }

    @GetMapping("/earning")
    public String countEarning(Model model, Principal principal) throws NotFoundException {
        User user = userService.findByUserName(principal.getName()).orElseThrow(()->new NotFoundException("Nie znaleziono"));
        List<Apartment> allApartmentsUser = userService.findApartmentsByUser(user);
        model.addAttribute("count", countBillsService.countBills(user));

        model.addAttribute("apartments",allApartmentsUser.size());

        model.addAttribute("rentedApartments", countBillsService.getMyRentedApartmentsCount(allApartmentsUser));

        model.addAttribute("earningFromRentedApartment", countBillsService.countEarningFromRentedApartment(allApartmentsUser));

        model.addAttribute("costsApartments",countBillsService.fullCostsUpKeepApartments(allApartmentsUser));

        model.addAttribute("costsUnrentedApartments", countBillsService.costsUnrentedApartments(allApartmentsUser) );

        model.addAttribute("fault", countBillsService.getCostsFaultByApartments(allApartmentsUser));
        return "user/earning";
    }
}
