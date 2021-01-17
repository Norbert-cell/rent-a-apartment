package pl.coderslab.rentaapartment.controller;

import javassist.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.rentaapartment.model.Address;
import pl.coderslab.rentaapartment.model.Apartment;
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

    @GetMapping("/auctions")
    public String myApartments(Model model, Principal principal) throws NotFoundException {
        User user = userService.findByUserName(principal.getName()).orElseThrow(()->new NotFoundException("Nie znaleziono"));
        List<Apartment> activeApartments = apartmentService.userActiveAuctions(user);
        List<Apartment> rentedApartment = apartmentService.userRentedApartment(user);
        model.addAttribute("auctions", activeApartments);
        model.addAttribute("rentedApartment",rentedApartment );
        return "user/auctions";
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
        return "redirect:/app/";
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
        return "redirect:/app/";
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
        double countBills = countBillsService.countBills(user);
        List<Apartment> rentedApartmentsByUser = apartmentService.findRentedApartmentsByUser(user);
        List<Apartment> allApartmentsUser = apartmentService.findApartmentByUser(user);
        model.addAttribute("count", countBills);
        model.addAttribute("apartments",apartmentService.findApartmentByUser(user).size());
        model.addAttribute("rentedApartments", rentedApartmentsByUser.size());
        model.addAttribute("earningFromRentedApartment", countEarningFromRentedApartment(rentedApartmentsByUser));
        model.addAttribute("costsApartments", fullCostsUpKeepApartments(allApartmentsUser));
        return "user/earning";
    }


    public double countEarningFromRentedApartment(List<Apartment> apartments){
        return apartments.stream()
                .filter(x -> x.getTenantUser() != null)
                .map(x -> x.getPrice() - x.getMyBills())
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    public double fullCostsUpKeepApartments(List<Apartment> apartments){
        return apartments.stream()
                .map(Apartment::getMyBills)
                .mapToDouble(Double::doubleValue)
                .sum();
    }

}
