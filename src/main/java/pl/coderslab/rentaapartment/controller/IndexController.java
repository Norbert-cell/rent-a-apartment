package pl.coderslab.rentaapartment.controller;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.coderslab.rentaapartment.model.User;
import pl.coderslab.rentaapartment.service.UserService;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Controller
public class IndexController {

    private UserService userService;

    public IndexController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/")
    public String index(){
        return "views/index";
    }

    @GetMapping("/login")
    public String login(){
        return "views/login";
    }
    @PostMapping("/login")
    public String successLogin(){
        return "views/dashboard";
    }

    @GetMapping("/registry")
    public String initRegistry(Model model){
        model.addAttribute("user", new User());
        return "registry";
    }
    @PostMapping("/registry")
    @ResponseBody
    public String registry(@ModelAttribute @Valid User user, BindingResult result){
        if(result.hasErrors()){
            return "registry";
        }
        user.setRole("USER");
        user.setCreated(LocalDateTime.now());
        userService.persistUser(user);
        return "dodano";
    }
}
