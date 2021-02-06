package pl.coderslab.rentaapartment.controller;


import javassist.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.rentaapartment.model.Apartment;
import pl.coderslab.rentaapartment.model.Image;
import pl.coderslab.rentaapartment.model.User;
import pl.coderslab.rentaapartment.service.AddressService;
import pl.coderslab.rentaapartment.service.ApartmentService;
import pl.coderslab.rentaapartment.service.ImageService;
import pl.coderslab.rentaapartment.service.UserService;
import pl.coderslab.rentaapartment.validator.AddressValidationGroup;
import pl.coderslab.rentaapartment.validator.ApartmentValidationGroup;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/app/apartment")
public class ApartmentController {

    private final UserService userService;
    private final ApartmentService apartmentService;
    private final AddressService addressService;
    private final ImageService imageService;

    public ApartmentController(UserService userService, ApartmentService apartmentService, AddressService addressService, ImageService imageService) {
        this.userService = userService;
        this.apartmentService = apartmentService;
        this.addressService = addressService;
        this.imageService = imageService;
    }

    @GetMapping("/add-apartment")
    public String initaddApartment(Model model){
        model.addAttribute("apartment", new Apartment());
        return "apartment/add";
    }

    @PostMapping("/add-apartment")
    public String addApartment(@ModelAttribute @Validated({ApartmentValidationGroup.class, AddressValidationGroup.class}) Apartment apartment,
                               BindingResult result, Principal principal,@RequestParam("images") List<MultipartFile> images) throws NotFoundException {
        if (result.hasErrors()){
            return "apartment/add";
        }
        List<Image> imagesList = imageService.uploadFile(images);

        apartment.setImages(imagesList);

        User owner = userService.findByUserName(principal.getName()).orElseThrow(()->new NotFoundException("Nie znaleziono"));
        apartment.setCreated(LocalDateTime.now());
        apartment.setRented(false);
        apartment.setOwnerUser(owner);
        addressService.saveAddress(apartment.getAddress());
        apartmentService.saveApartment(apartment);
        return "redirect:/app/1";
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
    public String detailsApartment(@PathVariable long id, Model model, Principal principal) throws NotFoundException {
        long userId = userService.findByUserName(principal.getName()).orElse(new User()).getId();
        Apartment apartment = apartmentService.findById(id).orElseThrow(()->new NotFoundException("Nie znaleziono"));
        model.addAttribute("apartment", apartment);
        model.addAttribute("senderId",userId);
        return "apartment/detailsApartment";
    }

    @GetMapping("/edit/{id}")
    public String editApartment(Model model,@PathVariable long id, Principal principal) throws NotFoundException {
        User ownerUser = userService.findByUserName(principal.getName()).orElseThrow(()->new NotFoundException("No value present"));
        Optional<Apartment> apartmentToEdit = apartmentService.findById(id);
        boolean foundedApartment = apartmentService.findApartmentByUserToCheckIsOwnerUser(ownerUser,apartmentToEdit);
        boolean isRentedApartment = apartmentService.checkIsRentedApartment(apartmentToEdit);
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
                                BindingResult result, @RequestParam("images") List<MultipartFile> images){
        if (result.hasErrors()){
            return "apartment/edit";
        }
        List<Image> imagesList = imageService.uploadFile(images);

        apartment.setImages(imagesList);
        apartment.setUpdated(LocalDateTime.now());
        addressService.saveAddress(apartment.getAddress());
        apartmentService.saveApartment(apartment);
    return "redirect:/app/user/auctions/1";
    }

    @GetMapping("/edit/error")
    public String get(Model model){
        model.addAttribute("notOwnerUserError", "Ten apartament nie nalezy do Ciebie!");
        return "apartment/errorNotOwnerUser";
    }

    @GetMapping("/remove/{apartmentId}")
    public String removeApartment(@PathVariable long apartmentId, Principal principal, Model model) throws NotFoundException {
        User user = userService.findByUserName(principal.getName()).orElse(new User());

        boolean isOwnerUser = apartmentService.checkIsOwnerUser(user, apartmentId);
        if (isOwnerUser){
            boolean remove = apartmentService.remove(apartmentId);
            if (!remove){
                model.addAttribute("notOwnerUserError", "Ten apartament nie nalezy do Ciebie!");
                return "apartment/failureRemove";
            }
            return "apartment/succesfullRemove";
        }
        return "apartment/errorNotOwnerUser";


    }

    @GetMapping("/termination/{apartmentId}")
    public String terminationApartment(@PathVariable long apartmentId, Principal principal, Model model){
        User user = userService.findByUserName(principal.getName()).orElse(new User());
        boolean isOwnerUser = apartmentService.checkIsOwnerUser(user,apartmentId);

        if (isOwnerUser){
            Apartment apartment = apartmentService.findById(apartmentId).orElse(new Apartment());
            apartment.setTenantUser(null);
            apartment.setRented(false);
            apartmentService.saveApartment(apartment);
            return "redirect:/app/1";
        }
        model.addAttribute("notOwnerUserError", "Ten apartament nie nalezy do Ciebie!");
        return "apartment/errorNotOwnerUser";
    }

}
