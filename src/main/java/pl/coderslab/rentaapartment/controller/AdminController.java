package pl.coderslab.rentaapartment.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.rentaapartment.model.Role;
import pl.coderslab.rentaapartment.model.User;
import pl.coderslab.rentaapartment.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    public void allUsers(Model model){
        model.addAttribute("users",userService.findAll());
    }

    @GetMapping("/users")
    @Secured("ADMIN")
    public String users(){
        return "admin/users";
    }

    @GetMapping("/role/{id}")
    public String changeRole(@PathVariable long id){
        User user = userService.findById(id).orElse(new User());
        if (user.getRole().equals(Role.ROLE_FIRM)){
            return "admin/users";
        }
        userService.changeRole(user);
        return "redirect:/admin/users";
    }
}
