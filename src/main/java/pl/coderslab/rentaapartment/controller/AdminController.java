package pl.coderslab.rentaapartment.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/admin")
public class AdminController {

    @GetMapping("/")
    @Secured("ADMIN")
    public String hello(){
        return "HELLO ADMIN";
    }
}
