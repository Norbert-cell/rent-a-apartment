package pl.coderslab.rentaapartment.service;

import javassist.NotFoundException;
import org.hibernate.annotations.Check;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.rentaapartment.model.Apartment;
import pl.coderslab.rentaapartment.model.Message;
import pl.coderslab.rentaapartment.model.MessageType;
import pl.coderslab.rentaapartment.model.User;
import pl.coderslab.rentaapartment.repository.ApartmentRepository;
import pl.coderslab.rentaapartment.repository.MessageRepository;
import pl.coderslab.rentaapartment.repository.UserRepository;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class MessageService {

    private final MessageRepository messageRepository;
    private final ApartmentRepository apartmentRepository;
    private final UserRepository userRepository;

    public MessageService(MessageRepository messageRepository, ApartmentRepository apartmentRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.apartmentRepository = apartmentRepository;
        this.userRepository = userRepository;
    }


    public User getOwnerUserFromApartment(long apartmentId) {
        Optional<Apartment> apartment = apartmentRepository.findById(apartmentId);
        return apartment.stream().map(Apartment::getOwnerUser).findFirst().orElse(new User());
    }

    public void saveMessage(Message message, String senderUserName) {
        message.setSenderUser(userRepository.findByUserName(senderUserName).orElse(new User()).getId());
        message.setDateOfSendMsg(LocalDateTime.now());
        message.setRead(false);
        messageRepository.save(message);
    }

    public boolean findMyrentedApartmentByUserNameAndApartmentId(String name, long apartmentId) throws NotFoundException {
        User userToCheckIsrentedApartmentbyhim = userRepository.findByUserName(name).orElseThrow(() -> new NotFoundException("Nie ma uzytkownika"));
        Apartment apartmentByTenantUser = apartmentRepository.findApartmentByTenantUser(userToCheckIsrentedApartmentbyhim);
        return apartmentByTenantUser.getId() == apartmentId;
    }

    public String getApartmentTitleByApartmentId(long apartmentId) {
        return apartmentRepository.findById(apartmentId).stream().map(Apartment::getTitle).findFirst().orElse("Nieznany tytul");
    }

    public List<Message> findAllMessagesByTypeAndReceiverUser(MessageType type, long receiveUser) {
        return messageRepository.findAllByTypeAndReceiverUser(type,receiveUser);
    }

    public List<Message> findAllMessagesByUserSenderIdToReceiverUser(long userId, long receiverUserId) {
        return messageRepository.findAllBySenderUserAndReceiverUser(userId,receiverUserId);
    }

    public String getSenderUserFullNameByUserId(long userId) {
        return userRepository.findById(userId).orElse(new User()).getFullName();
    }

    public Message findById(long messageId) {
        return messageRepository.findById(messageId).orElse(new Message());
    }

    public void updateReadMessage(Message msg) {
        messageRepository.save(msg);
    }

    public void saveReturnMessage(Message message) {
        message.setDateOfSendMsg(LocalDateTime.now());
        messageRepository.save(message);
    }

//    public Set<Long> findAllSenderByReceiverUserName(String name) {
//        long receiverUser = userRepository.findByUserName(name).orElse(new User()).getId();
////        List<Message> allByReceiverUser = messageRepository.findAllByReceiverUser(receiverUser);
////        return allByReceiverUser.stream().map(x->x.getSenderUser()).collect(Collectors.toSet());
//    }
//
//    public List<Message> findAllByReceiverUserAndSenderUserAndType(String name, long userId, MessageType normalMessage) {
//        long id = userRepository.findByUserName(name).orElse(new User()).getId();
//        return messageRepository.findAllByReceiverUserAndSenderUserAndType(id,userId,normalMessage);
//    }

    public Set<User> getAllSenderMessagesForUserByReceiverAndType(String name, MessageType messageType) {
        User user = userRepository.findByUserName(name).orElse(new User());
        List<Message> allByReceiverUser = messageRepository.findAllByReceiverUserAndTypeOrderByDateOfSendMsgDesc(user.getId(), messageType);
        Set<Long> senderIdList = allByReceiverUser.stream()
                .map(Message::getSenderUser)
                .collect(Collectors.toSet());
        return senderIdList.stream()
                .map(x -> userRepository.findById(x).orElse(new User()))
                .collect(Collectors.toSet());
    }

    public List<Message> getMessagesBetweenUsersBySenderAndReceiverAndType(long senderId, String name, MessageType messageType) {
        User receiverUser = userRepository.findByUserName(name).orElse(new User());
        List<Message> messageByReceiver = messageRepository.findAllByReceiverUserAndSenderUserAndTypeOrderByDateOfSendMsgDesc(receiverUser.getId(), senderId, messageType);
        List<Message> messageBySender = messageRepository.findAllBySenderUserAndReceiverUserAndTypeOrderByDateOfSendMsgDesc(receiverUser.getId(),senderId, messageType);

        System.out.println(messageByReceiver);
        System.out.println(messageBySender);
        List<Message> concatAllMessages = new ArrayList<>();
        concatAllMessages.addAll(messageByReceiver);
        concatAllMessages.addAll(messageBySender);

        concatAllMessages.sort(Comparator.comparing(Message::getDateOfSendMsg));
        return concatAllMessages;
    }

    public void updateReadMessageWherePrincipalIsAndMessageId(String name, List<Message> messages) {
        User user = userRepository.findByUserName(name).orElse(new User());
        for (Message x : messages) {
            if (x.getReceiverUser() == user.getId()) {
                x.setRead(true);
            }
        }
        messages.forEach(messageRepository::save);

    }

    public int getUnReadMessagesSizeByUserIdAndType(long userId, MessageType messageType) {
        List<Message> msg = messageRepository.findAllByReceiverUserAndType(userId, messageType);
        return (int) msg.stream()
                .filter(x -> !x.isRead())
                .count();
    }
}
