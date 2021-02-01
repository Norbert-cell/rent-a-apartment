package pl.coderslab.rentaapartment.controller;

import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.rentaapartment.model.Apartment;
import pl.coderslab.rentaapartment.model.MessageType;
import pl.coderslab.rentaapartment.model.Role;
import pl.coderslab.rentaapartment.model.User;
import pl.coderslab.rentaapartment.service.ApartmentService;
import pl.coderslab.rentaapartment.service.MessageService;
import pl.coderslab.rentaapartment.service.UserService;

import java.security.Principal;

import static pl.coderslab.rentaapartment.model.MessageType.FAULT_MESSAGE;
import static pl.coderslab.rentaapartment.model.MessageType.NORMAL_MESSAGE;


@Controller
@RequestMapping({"/app", "/app/"})
public class AppController {

    private ApartmentService apartmentService;
    private UserService userService;
    private MessageService messageService;

    public AppController(ApartmentService apartmentService, UserService userService, MessageService messageService) {
        this.apartmentService = apartmentService;
        this.userService = userService;
        this.messageService = messageService;
    }


    @GetMapping("/{pageId}")
    public String dashboard(@PathVariable int pageId, Model model, Principal principal) throws NotFoundException {
        User user = userService.findByUserName(principal.getName()).orElseThrow(()->new NotFoundException("Nie znaleziono"));
        int normalMessages = messageService.getUnReadMessagesSizeByUserIdAndType(user.getId(), NORMAL_MESSAGE);
        int faultMessages = messageService.getUnReadMessagesSizeByUserIdAndType(user.getId(),FAULT_MESSAGE);
        Role authority = user.getRole();
        if(authority.equals(Role.ROLE_ADMIN)){
            model.addAttribute("admin", true);
        }
        final int pageSize =5;
        Pageable paging = PageRequest.of(pageId-1,pageSize);
        Page<Apartment> allByTenantUserIsNull = apartmentService.findAllByTenantUserIsNull(paging);
        model.addAttribute("totalPages", allByTenantUserIsNull.getTotalPages());
        model.addAttribute("listApartments",allByTenantUserIsNull.getContent());
        model.addAttribute("normalMessagesSize", normalMessages);
        model.addAttribute("faultMessagesSize", faultMessages);

        return "dashboard/dashboard";
    }


}
