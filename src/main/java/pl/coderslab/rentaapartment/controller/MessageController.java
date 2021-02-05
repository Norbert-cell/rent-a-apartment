package pl.coderslab.rentaapartment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.rentaapartment.model.Message;
import pl.coderslab.rentaapartment.model.MessageType;
import pl.coderslab.rentaapartment.model.User;
import pl.coderslab.rentaapartment.service.MailService;
import pl.coderslab.rentaapartment.service.MessageService;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;


@Controller
@RequestMapping("/app/message")
public class MessageController {

    private final MessageService messageService;
    private final MailService mailService;

    public MessageController(MessageService messageService, MailService mailService) {
        this.messageService = messageService;
        this.mailService = mailService;
    }

    @GetMapping("/send/{apartmentId}/{senderId}")
    public String sendMsg(@RequestParam("type") MessageType messageType ,@PathVariable long apartmentId,
                                @PathVariable long senderId, Model model, Principal principal){
        User principalUser = messageService.getPrincipalUser(principal.getName());

        Message message = new Message();
        message.setType(messageType);
        message.setMsgAboutApartmentId(apartmentId);
        message.setTitle(messageService.getApartmentTitleByApartmentId(apartmentId));
        message.setSenderUserId(principalUser.getId());
        message.setReceiverUserId(senderId);
        model.addAttribute("message",message);
        if (messageType.equals(MessageType.NORMAL)) {
            return "message/messageAboutRent";
        }
        return "message/reportFault";
    }

    @PostMapping("/send")
    public String sendMsg(@ModelAttribute @Valid Message message, BindingResult result){
        if (result.hasErrors()){
            return "message/messageAboutRent";
        }
        sendMail(message.getSenderUserId(),message.getReceiverUserId(),message.getType());
        message.setDateOfSendMsg(LocalDateTime.now());
        messageService.saveMessage(message);
        return "message/succesMsgSent";
    }

    @GetMapping("/msg")
    public String allMessages(@RequestParam("type") MessageType messageType, Model model, Principal principal){
        List<User> senderUser = messageService.getAllSenderUserByPrincipalAndType(principal.getName(), messageType);
        model.addAttribute("senderUsers",senderUser);
        model.addAttribute("noMessages", senderUser.size());
        if (messageType.equals(MessageType.NORMAL)){
            return "message/messages";
        }
        return "message/faultMessages";
    }

    @GetMapping("/msg/{senderId}")
    public String getMessages(@RequestParam("type") MessageType messageType,@PathVariable long senderId,
                                    Model model, Principal principal){
        List<Message> messages = messageService.getMessagesBetweenUsersBySenderAndReceiver(senderId,principal.getName(),messageType);

        messageService.updateReadMessageWherePrincipalIsAndMessagesIs(principal.getName(),messages);

        String title = messageService.getTitleMessages(messages);


        model.addAttribute("userFullName",messageService.getSenderUserFullNameByUserId(senderId));
        model.addAttribute("messageTitle", title);
        model.addAttribute("messages",messages);
        model.addAttribute("msgAboutApartmentId",messageService.getApartmentIdByMessages(messages));
        model.addAttribute("senderId", senderId);
        if (messageType.equals(MessageType.NORMAL)){
            return "message/messageFromUser";
        }
    return "message/myFaultMessages";
    }

    public void sendMail(long senderId, long receiverId, MessageType messageType){

        String fullName = messageService.getUserById(senderId).getFullName();
        String userName = messageService.getUserById(receiverId).getUserName();
        String url = "http://localhost:8080/app/message/msg?type="+messageType;
        String msg = "Hej! Własnie dostales wiadomosc od " + fullName +". Wejdz w ten link aby sprawdzic : ";

        try {
            mailService.sendMail(userName,"Nowa wiadomosc z portalu rentaapartment", msg + url, false);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


}
