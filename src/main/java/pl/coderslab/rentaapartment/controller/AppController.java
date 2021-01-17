package pl.coderslab.rentaapartment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.rentaapartment.model.Apartment;
import pl.coderslab.rentaapartment.service.ApartmentService;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/app")
public class AppController {

    private ApartmentService apartmentService;

    public AppController(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    @ModelAttribute("listApartments")
    public List<Apartment> allApartments() {
        List<Apartment> apartments = apartmentService.findAll();
        List<Apartment> collect = apartments.stream()
                .filter(x -> !x.isRented())
                .collect(Collectors.toList());
        return collect;
    }

    @GetMapping("/")
    public String dashboard(){
        return "dashboard/dashboard";
    }


}
