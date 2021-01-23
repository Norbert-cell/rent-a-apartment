package pl.coderslab.rentaapartment.controller;


import javassist.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.rentaapartment.model.Apartment;
import pl.coderslab.rentaapartment.model.User;
import pl.coderslab.rentaapartment.service.AddressService;
import pl.coderslab.rentaapartment.service.ApartmentService;
import pl.coderslab.rentaapartment.service.UserService;
import pl.coderslab.rentaapartment.validator.AddressValidationGroup;
import pl.coderslab.rentaapartment.validator.ApartmentValidationGroup;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/app/apartment")
public class ApartmentController {

    private final UserService userService;
    private final ApartmentService apartmentService;
    private final AddressService addressService;

    public ApartmentController(UserService userService, ApartmentService apartmentService, AddressService addressService) {
        this.userService = userService;
        this.apartmentService = apartmentService;
        this.addressService = addressService;
    }


    @GetMapping("/rent/{id}")
    public String rentApartment(@PathVariable long id, Principal principal) throws NotFoundException {
        User tenantUser = userService.findByUserName(principal.getName()).orElseThrow(()->new NotFoundException("Nie znaleziono"));
        Apartment apartment = apartmentService.findById(id).orElseThrow(()->new NotFoundException("Nie znaleziono"));
        if(apartment.getOwnerUser().getId() == tenantUser.getId() || tenantUser.getRentedHouse() != null){
            return "redirect:/app/1";
        }
        apartment.setTenantUser(tenantUser);
        apartment.setRented(true);
        apartmentService.saveApartment(apartment);
        return "redirect:/app/1";
    }

    @GetMapping("/details/{id}")
    public String detailsApartment(@PathVariable long id, Model model) throws NotFoundException {
        Apartment apartment = apartmentService.findById(id).orElseThrow(()->new NotFoundException("Nie znaleziono"));
        model.addAttribute("apartment", apartment);
        return "apartment/detailsApartment";
    }

    @GetMapping("/edit/{id}")
    public String editApartment(Model model,@PathVariable long id, Principal principal) throws NotFoundException {
        User ownerUser = userService.findByUserName(principal.getName()).orElseThrow(()->new NotFoundException("No value present"));
        Optional<Apartment> apartmentToEdit = apartmentService.findById(id);
        boolean foundedApartment = apartmentService.findApartmentByUser(ownerUser).stream()
                .anyMatch(x-> x.getOwnerUser() == apartmentToEdit.map(Apartment::getOwnerUser).orElse(new User()));
        boolean isRentedApartment = apartmentToEdit.stream().anyMatch(Apartment::isRented);
        if(foundedApartment) {
            if(isRentedApartment){
                return "redirect:/app/user/auctions/1";
            }
            model.addAttribute("apartment", apartmentToEdit);
        } else {
            return "redirect:/app/apartment/edit/error";
        }

    return "apartment/edit";
    }

    @PostMapping("/edit/{id}")
    public String editApartment(@ModelAttribute @Validated({ApartmentValidationGroup.class, AddressValidationGroup.class}) Apartment apartment,
                                BindingResult result){
        if (result.hasErrors()){
            return "apartment/edit";
        }
        apartment.setUpdated(LocalDateTime.now());
        addressService.saveAddress(apartment.getAddress());
        apartmentService.saveApartment(apartment);
    return "redirect:/app/user/auctions/1";
    }

    @GetMapping("/edit/error")
    public String get(Model model){
        model.addAttribute("notOwnerUserError", "Nie mozesz edytowac apartamentu. Ten apartament nie nalezy do Ciebie!");
        return "apartment/errorNotOwnerUser";
    }
}
