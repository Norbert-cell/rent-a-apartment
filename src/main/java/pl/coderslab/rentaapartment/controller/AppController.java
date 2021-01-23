package pl.coderslab.rentaapartment.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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


    @GetMapping("/{pageId}")
    public String dashboard(@PathVariable int pageId, Model model){
        final int pageSize =5;
        Pageable paging = PageRequest.of(pageId-1,pageSize);
        Page<Apartment> allByTenantUserIsNull = apartmentService.findAllByTenantUserIsNull(paging);
        model.addAttribute("totalPages", allByTenantUserIsNull.getTotalPages());
        model.addAttribute("listApartments",allByTenantUserIsNull.getContent());
        return "dashboard/dashboard";
    }


}
