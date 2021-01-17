package pl.coderslab.rentaapartment.controller;

import javassist.NotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.rentaapartment.model.Role;
import pl.coderslab.rentaapartment.model.Token;
import pl.coderslab.rentaapartment.model.User;
import pl.coderslab.rentaapartment.repository.TokenRepository;
import pl.coderslab.rentaapartment.service.MailService;
import pl.coderslab.rentaapartment.service.UserService;
import pl.coderslab.rentaapartment.validator.FirmValidationGroup;
import pl.coderslab.rentaapartment.validator.UserValidationGroup;

import javax.mail.MessagingException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@Controller
public class IndexController {

    private final UserService userService;
    private final TokenRepository tokenRepository;
    private final MailService mailService;
    private PasswordEncoder passwordEncoder;

    public IndexController(UserService userService, TokenRepository tokenRepository, MailService mailService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.tokenRepository = tokenRepository;
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;
    }
    @ModelAttribute("roles")
    public List<String> roles(){
        return Arrays.asList("Osoba prywatna", "Firma");
    }

    @GetMapping("/")
    public String index(){
        return "index/index";
    }

    @GetMapping("/login")
    public String login() {
        return "index/login";
    }
    @PostMapping("/login")
    public String successLogin(@RequestParam boolean error, Model model){
        if(error = true) {
            model.addAttribute("errorMessage", "Nie poprawny email lub haslo, lub nie aktywowales konta!");
            return "index/login";
        }
        return "redirect:/app/";
    }

    @GetMapping("/logout")
    public String initlogout(){
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(){
            return "redirect:/";
    }

    @GetMapping("/registry-choose")
    public String chooseRegistry(){
        return "index/registry";
    }

    @GetMapping("/registry-firm")
    public String registry(Model model){
        model.addAttribute("user", new User());
        return "index/registryFirm";
    }

    @PostMapping("/registry-firm")
    public String registryFirm(@ModelAttribute @Validated({FirmValidationGroup.class}) User user, BindingResult result){
        if(result.hasErrors()){
            return "index/registryFirm";
        }
        user.setRole(Role.ROLE_FIRM);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreated(LocalDateTime.now());
        userService.saveUser(user);
        sendToken(user);
        return "redirect:/login";
    }

    private void sendToken(User user){
        String value = UUID.randomUUID().toString();
        Token token = new Token();
        token.setValue(value);
        token.setUser(user);
        tokenRepository.save(token);

        String url = "http://localhost:8080/token?value="+value;
        String msg = "Link aktywacyjny do naszego portalu! Kliknij w niego";

        try {
            mailService.sendMail(user.getUserName(),"Link aktywacyjny do portalu RentApartment",msg + url,false);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/token")
    public String checkToken(@RequestParam String value) {
        Token token = tokenRepository.findByValue(value).orElseThrow(()-> new RuntimeException("Nie ma takiego tokena"));
        User user = token.getUser();
        user.setEnabled(true);
        userService.saveUser(user);
        tokenRepository.delete(token);
        return "redirect:/login";
    }

    @GetMapping("/registry-user")
    public String registryUser(Model model){
        model.addAttribute("user", new User());
        return "index/registryUser";
    }
    @PostMapping("/registry-user")
    public String registryUser(@ModelAttribute @Validated({UserValidationGroup.class}) User user, BindingResult result){
        if(result.hasErrors()){
            return "index/registryUser";
        }
        user.setRole(Role.ROLE_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreated(LocalDateTime.now());
        userService.saveUser(user);
        sendToken(user);
        return "redirect:/login";
    }
}
