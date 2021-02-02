package pl.coderslab.rentaapartment.controller;

import javassist.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.rentaapartment.model.Message;
import pl.coderslab.rentaapartment.model.MessageType;
import pl.coderslab.rentaapartment.model.User;
import pl.coderslab.rentaapartment.service.MessageService;
import pl.coderslab.rentaapartment.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static pl.coderslab.rentaapartment.model.MessageType.FAULT_MESSAGE;
import static pl.coderslab.rentaapartment.model.MessageType.NORMAL_MESSAGE;

@Controller
@RequestMapping("/app/message")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/send/{apartmentId}")
    public String sendNormalMsg(@PathVariable long apartmentId, Model model,Principal principal){
        User principalUser = messageService.getPrincipalUser(principal.getName());
        User ownerUser = messageService.getOwnerUserOfApartmentById(apartmentId);
        if (principalUser.getId() == ownerUser.getId()){
            return "redirect:/app/1";
        }
        model.addAttribute("ownerUserFullName",ownerUser.getFullName());
        Message message = new Message();
        message.setMsgAboutApartmentId(apartmentId);
        message.setType(MessageType.NORMAL_MESSAGE);
        message.setTitle(messageService.getApartmentTitleByApartmentId(apartmentId));
        message.setUsers(Arrays.asList(ownerUser,principalUser));
        model.addAttribute("message", message);
        return "message/messageAboutRent";
    }

    @PostMapping("/send")
    public String sendNormalMsg(@ModelAttribute @Valid Message message, BindingResult result){
        if (result.hasErrors()){
            return "message/messageAboutRent";
        }
        message.setDateOfSendMsg(LocalDateTime.now());
        messageService.saveMessage(message);
        return "message/succesMsgSent";
    }

    @GetMapping("/normal")
    public String allNormalMessages(Model model, Principal principal){
        Set<User> senderUser = messageService.getAllSenderUserByPrincipalAndType(principal.getName(), NORMAL_MESSAGE);
        List<User> users = messageService.convertSetToListAndSortByRead(senderUser);
        model.addAttribute("senderUsers",users);
        model.addAttribute("noMessages", senderUser.size());
    return "message/messages";
    }

    @GetMapping("/fault")
    public String allFaultMessages(Model model, Principal principal){
        Set<User> senderUser = messageService.getAllSenderUserByPrincipalAndType(principal.getName(),FAULT_MESSAGE);
        List<User> users = messageService.convertSetToListAndSortByRead(senderUser);
        model.addAttribute("faultMessages",users);
        model.addAttribute("noMessages",senderUser.size());
    return "message/faultMessages";
    }

    @GetMapping("/normal/{senderId}")
    public String getNormalMessages(@PathVariable long senderId, Model model, Principal principal){
        Set<Message> messages = messageService.getMessagesBetweenUsersBySenderAndReceiver(senderId,principal.getName(),NORMAL_MESSAGE);

        List<Message> messageList = messageService.convertSetToListAndSortByDate(messages);

        long apartmentIdByMessages = messageService.getApartmentIdByMessagesSet(messages);

        String title = messageService.getTitleMessages(messages);

        messageService.updateReadMessageWherePrincipalIsAndMessageId(principal.getName(), messages);

        model.addAttribute("userFullName",messageService.getSenderUserFullNameByUserId(senderId));
        model.addAttribute("messageTitle", title);
        model.addAttribute("messages",messageList);
        model.addAttribute("msgAboutApartmentId",apartmentIdByMessages);
        model.addAttribute("senderId", senderId);
        return "message/messageFromUser";
    }

    @GetMapping("/fault/{ownerUserId}")
    public String getFaultMessages(@PathVariable long ownerUserId, Model model, Principal principal){
        Set<Message> messages = messageService.getMessagesBetweenUsersBySenderAndReceiver(ownerUserId,principal.getName(),FAULT_MESSAGE);

        List<Message> messageList = messageService.convertSetToListAndSortByDate(messages);

        long apartmentIdByMessages = messageService.getApartmentIdByMessagesSet(messages);

        String title = messageService.getTitleMessages(messages);

        messageService.updateReadMessageWherePrincipalIsAndMessageId(principal.getName(),messages);

        model.addAttribute("messageTitle", title);
        model.addAttribute("messages",messageList);
        model.addAttribute("msgAboutApartmentId",apartmentIdByMessages);
        model.addAttribute("ownerUserId", ownerUserId);

    return "message/myFaultMessages";
    }

    @GetMapping("/return/{msgAboutApartmentId}")
    public String returnMessage(@PathVariable long msgAboutApartmentId, Model model, Principal principal){
        User ownerUserOfApartmentById = messageService.getOwnerUserOfApartmentById(msgAboutApartmentId);
        User principalUser = messageService.getPrincipalUser(principal.getName());

        Message messageById = messageService.findMessageBetweenOwnerAndPrincipalByMsgAboutApartmentIdAndUsers
                (msgAboutApartmentId,ownerUserOfApartmentById,principalUser);
        Message message = new Message();
        message.setUsers(messageById.getUsers());
        message.setMsgAboutApartmentId(messageById.getMsgAboutApartmentId());
        message.setType(messageById.getType());
        message.setTitle(messageById.getTitle());

        model.addAttribute("title", messageById.getTitle());
        model.addAttribute("message", message);
        return "message/returnNormalMessages";
    }
    @PostMapping("/return")
    public String returnMessage(@ModelAttribute @Valid Message message, BindingResult result){
        if (result.hasErrors()){
            if (message.getType().equals(NORMAL_MESSAGE)){
                return "message/returnNormalMessages";
            }
            return "message/returnMessage";
        }
        message.setDateOfSendMsg(LocalDateTime.now());
        messageService.saveMessage(message);

        return "message/succesMsgSent";
    }

    @GetMapping("/report/{apartmentId}")
    public String reportFault(@PathVariable long apartmentId,Principal principal, Model model) throws NotFoundException {
        boolean foundMyRentedApartment = messageService.findMyrentedApartmentByUserNameAndApartmentId(principal.getName(),apartmentId);
        if (!foundMyRentedApartment){
            return "redirect:/app/1";
        }
        User tenantUser = messageService.getPrincipalUser(principal.getName());
        User ownerUser = messageService.getOwnerUserOfApartmentById(apartmentId);
        Message message = new Message();
        message.setMsgAboutApartmentId(apartmentId);
        message.setUsers(Arrays.asList(ownerUser,tenantUser));
        model.addAttribute("message", message);

        return "message/reportFault";
    }

    @PostMapping("/report")
    public String reportFault(@ModelAttribute @Valid Message message, BindingResult result){
        if (result.hasErrors()){
            return "message/reportFault";
        }
        message.setType(FAULT_MESSAGE);
        message.setDateOfSendMsg(LocalDateTime.now());
        messageService.saveMessage(message);
    return "message/succesMsgSent";
    }

    @GetMapping("/return-fault/{msgAboutApartmentId}")
    public String returnFault(@PathVariable long msgAboutApartmentId, Model model, Principal principal){
        User ownerUserOfApartmentById = messageService.getOwnerUserOfApartmentById(msgAboutApartmentId);
        User principalUser = messageService.getPrincipalUser(principal.getName());

        Message messageById = messageService.findMessageBetweenOwnerAndPrincipalByMsgAboutApartmentIdAndUsers
                (msgAboutApartmentId,ownerUserOfApartmentById,principalUser);
        Message message = new Message();
        message.setUsers(messageById.getUsers());
        message.setMsgAboutApartmentId(messageById.getMsgAboutApartmentId());
        message.setType(FAULT_MESSAGE);
        message.setTitle(messageById.getTitle());

        model.addAttribute("title", messageById.getTitle());
        model.addAttribute("message", message);
    return "message/returnMessage";
    }

}
