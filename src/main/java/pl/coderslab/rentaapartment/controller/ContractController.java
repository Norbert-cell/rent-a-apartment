package pl.coderslab.rentaapartment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.rentaapartment.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/app/contract")
public class ContractController {

    private final UserService userService;

    public ContractController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/{id}")
    public String get(@PathVariable long id, Principal principal){
        userService.findByUserName(principal.getName());
        userService.findById(id);
    return "";
    }
}
