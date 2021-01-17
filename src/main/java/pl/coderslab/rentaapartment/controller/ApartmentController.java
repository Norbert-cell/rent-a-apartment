package pl.coderslab.rentaapartment.controller;


import javassist.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.rentaapartment.model.Apartment;
import pl.coderslab.rentaapartment.model.User;
import pl.coderslab.rentaapartment.service.ApartmentService;
import pl.coderslab.rentaapartment.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/app/apartment")
public class ApartmentController {

    private final UserService userService;
    private final ApartmentService apartmentService;

    public ApartmentController(UserService userService, ApartmentService apartmentService) {
        this.userService = userService;
        this.apartmentService = apartmentService;
    }


    @GetMapping("/rent/{id}")
    public String rentApartment(@PathVariable long id, Principal principal) throws NotFoundException {
        User tenantUser = userService.findByUserName(principal.getName()).orElseThrow(()->new NotFoundException("Nie znaleziono"));
        Apartment apartment = apartmentService.findById(id).orElseThrow(()->new NotFoundException("Nie znaleziono"));
        if(apartment.getOwnerUser().getId() == tenantUser.getId()){
            return "redirect:/app/";
        }
        apartment.setTenantUser(tenantUser);
        apartment.setRented(true);
        apartmentService.saveApartment(apartment);
        return "redirect:/app/";
    }

    @GetMapping("/details/{id}")
    public String detailsApartment(@PathVariable long id, Model model) throws NotFoundException {
        Apartment apartment = apartmentService.findById(id).orElseThrow(()->new NotFoundException("Nie znaleziono"));
        model.addAttribute("apartment", apartment);
        return "apartment/detailsApartment";
    }
}
