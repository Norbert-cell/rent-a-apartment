package pl.coderslab.rentaapartment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.rentaapartment.model.Message;
import pl.coderslab.rentaapartment.model.User;
import pl.coderslab.rentaapartment.service.MessageService;
import pl.coderslab.rentaapartment.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import static pl.coderslab.rentaapartment.model.MessageType.FAULT_MESSAGE;
import static pl.coderslab.rentaapartment.model.MessageType.NORMAL_MESSAGE;

@Controller
@RequestMapping("/app/message")
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;

    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @GetMapping("/normal")
    public String allNormalMessages(Model model, Principal principal){
        Set<User> senderUser = messageService.getAllSenderMessagesForUserByReceiverAndType(principal.getName(), NORMAL_MESSAGE);
        model.addAttribute("senderUsers",senderUser);
        model.addAttribute("noMessages", senderUser.size());
    return "message/messages";
    }

    @GetMapping("/fault")
    public String allFaultMessages(Model model, Principal principal){
        Set<User> senderUser = messageService.getAllSenderMessagesForUserByReceiverAndType(principal.getName(),FAULT_MESSAGE);
        model.addAttribute("faultMessages",senderUser);
        model.addAttribute("noMessages",senderUser.size());
    return "message/faultMessages";
    }

    @GetMapping("/normal/{senderId}")
    public String getNormalMessages(@PathVariable long senderId, Model model, Principal principal){
        List<Message> messages = messageService.getMessagesBetweenUsersBySenderAndReceiverAndType(senderId,principal.getName(),NORMAL_MESSAGE);
        String title = messages.stream().map(Message::getTitle).findFirst().orElse("Brak tytulu");

        messageService.updateReadMessageWherePrincipalIsAndMessageId(principal.getName(), messages);

        model.addAttribute("userFullName",messageService.getSenderUserFullNameByUserId(senderId));
        model.addAttribute("messageTitle", title);
        model.addAttribute("messages",messages);
        return "message/messageFromUser";
    }


//    @GetMapping("/report-fault/{apartmentId}")
//    public String reportFault(@PathVariable long apartmentId, Model model, Principal principal) throws NotFoundException {
//        boolean foundMyRentedApartment = messageService.findMyrentedApartmentByUserNameAndApartmentId(principal.getName(),apartmentId);
//        if (!foundMyRentedApartment){
//            return "redirect:/app/1";
//        }
//        User ownerUserFromApartment = messageService.getOwnerUserFromApartment(apartmentId);
//        String title = messageService.getApartmentTitleByApartmentId(apartmentId);
//        Message message = new Message();
//        message.setMsgAboutApartmentId(apartmentId);
//        message.setReceiverUser(ownerUserFromApartment.getId());
//        message.setTitle(title);
//        message.setType(FAULT_MESSAGE);
//        model.addAttribute("ownerUserFullName", ownerUserFromApartment.getFullName());
//        model.addAttribute("message", message);
//    return "message/reportFault";
//    }
//
//    @PostMapping("/report-fault")
//    public String reportFault(@ModelAttribute Message message, BindingResult result, Principal principal){
//        if (result.hasErrors()){
//            return "message/reportFault";
//        }
//        messageService.saveMessage(message,principal.getName());
//    return "message/succesMsgSent";
//    }
//
//    @GetMapping("/fault")
//    public String allFaultMessage(Model model, Principal principal){
//        long receiveUser = userService.findByUserName(principal.getName()).orElse(new User()).getId();
//        List<Message> allMessagesByTypeAndReceiverUser = messageService.findAllByReceiverUserAndSenderUserAndType(principal.getName(),receiveUser,FAULT_MESSAGE);
//
//        Set<Long> senderId = allMessagesByTypeAndReceiverUser.stream().map(x -> x.getSenderUser()).collect(Collectors.toSet());
//
//        List<User> usersSender = new ArrayList<>();
//        for (Long id : senderId) {
//            usersSender.add(userService.findById(id).orElse(new User()));
//        }
//        model.addAttribute("faultMessages",usersSender);
//        model.addAttribute("noMessages",senderId.size());
//        return "message/faultMessages";
//    }
//
//    @GetMapping("/fault/{userId}")
//    public String messageFromUser(@PathVariable long userId, Principal principal, Model model){
//
//        long receiverUserId = userService.findByUserName(principal.getName()).orElse(new User()).getId();
//        List<Message> messages = messageService.findAllMessagesByUserSenderIdToReceiverUser(userId,receiverUserId);
//        String userFullName = messageService.getSenderUserFullNameByUserId(userId);
//        model.addAttribute("userFullName",userFullName);
//        model.addAttribute("messages", messages);
//    return "message/myFaultMessages";
//    }
//
//    @GetMapping("/return/{messageId}")
//    public String get(@PathVariable long messageId, Model model, Principal principal){
//        Message msg = messageService.findById(messageId);
//        msg.setRead(true);
//        messageService.updateReadMessage(msg);
//        String title = messageService.getApartmentTitleByApartmentId(msg.getMsgAboutApartmentId());
//        Message message = new Message();
//        message.setType(FAULT_MESSAGE);
//        message.setTitle(title);
//        message.setMsgAboutApartmentId(msg.getMsgAboutApartmentId());
//        message.setSenderUser(userService.findByUserName(principal.getName()).orElse(new User()).getId());
//        message.setReceiverUser(msg.getSenderUser());
//        model.addAttribute("title", title);
//        model.addAttribute("message", message);
//    return "message/returnMessage";
//    }
//
//    @PostMapping("/return")
//    public String get(@ModelAttribute Message message, BindingResult result){
//        if (result.hasErrors()){
//            return "message/returnMessage";
//        }
//        messageService.saveReturnMessage(message);
//
//    return "message/succesMsgSent";
//    }
//
//    @GetMapping("/rent/{apartmentId}")
//    public String messageAboutApartment(@PathVariable long apartmentId, Model model, Principal principal) {
//        long senderUserId = userService.findByUserName(principal.getName()).orElse(new User()).getId();
//        User ownerUserFromApartment = messageService.getOwnerUserFromApartment(apartmentId);
//        if(senderUserId == ownerUserFromApartment.getId()){
//            return "redirect:/app/1";
//        }
//        String apartmentTitle = messageService.getApartmentTitleByApartmentId(apartmentId);
//        Message message = new Message();
//        message.setReceiverUser(ownerUserFromApartment.getId());
//        message.setSenderUser(senderUserId);
//        message.setTitle(apartmentTitle);
//        message.setType(NORMAL_MESSAGE);
//        message.setMsgAboutApartmentId(apartmentId);
//        model.addAttribute("message", message);
//        model.addAttribute("apartmentTitle", apartmentTitle);
//        model.addAttribute("ownerUserFullName",ownerUserFromApartment.getFullName());
//        return "message/messageAboutRent";
//    }
//
//    @PostMapping("/rent")
//    public String messageRentApartment(@ModelAttribute Message message, BindingResult result, Principal principal){
//        if (result.hasErrors()){
//            return "message/messageAboutRent";
//        }
//        messageService.saveMessage(message, principal.getName());
//    return "message/succesMsgSent";
//    }
//
//    @GetMapping("/rent-message")
//    public String getMessageUser(Model model, Principal principal){
//        Set<Long> senderUsers = messageService.findAllSenderByReceiverUserName(principal.getName());
//        List<User> users = new ArrayList<>();
//        for (Long senderUser : senderUsers) {
//            users.add(userService.findById(senderUser).orElse(new User()));
//        }
//        model.addAttribute("senderUsers", users);
//        model.addAttribute("noMessages",users.size());
//    return "message/messages";
//    }
//
//    @GetMapping("/rent-message/{userId}")
//    public String getMessage(@PathVariable long userId, Model model, Principal principal){
//        List<Message> messages = messageService.findAllByReceiverUserAndSenderUserAndType(principal.getName(), userId, NORMAL_MESSAGE);
//        System.out.println(messages);
//        model.addAttribute("userFullName",messageService.getSenderUserFullNameByUserId(userId));
//        model.addAttribute("messages",messages);
//    return "message/messageFromUser";
//    }
//
//    @GetMapping("/rent-message/return/{messageId}")
//    public String returnMessage(@PathVariable long messageId, Model model, Principal principal){
//        Message msg = messageService.findById(messageId);
//        msg.setRead(true);
//        messageService.updateReadMessage(msg);
//        String title = messageService.getApartmentTitleByApartmentId(msg.getMsgAboutApartmentId());
//        Message message = new Message();
//        message.setType(FAULT_MESSAGE);
//        message.setTitle(title);
//        message.setMsgAboutApartmentId(msg.getMsgAboutApartmentId());
//        message.setSenderUser(userService.findByUserName(principal.getName()).orElse(new User()).getId());
//        message.setReceiverUser(msg.getSenderUser());
//        model.addAttribute("title", title);
//        model.addAttribute("message", message);
//    return "message/returnNormalMessages";
//    }
//    @PostMapping("/rent-message/return")
//    public String returnMessage(@ModelAttribute Message message, BindingResult result){
//        if (result.hasErrors()){
//            return "message/returnNormalMessages";
//        }
//        messageService.saveReturnMessage(message);
//
//        return "message/succesMsgSent";
//    }


}
