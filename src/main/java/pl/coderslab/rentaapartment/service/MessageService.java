package pl.coderslab.rentaapartment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.rentaapartment.model.Apartment;
import pl.coderslab.rentaapartment.model.Message;
import pl.coderslab.rentaapartment.model.MessageType;
import pl.coderslab.rentaapartment.model.User;
import pl.coderslab.rentaapartment.repository.ApartmentRepository;
import pl.coderslab.rentaapartment.repository.MessageRepository;
import pl.coderslab.rentaapartment.repository.UserRepository;

import java.util.*;

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

    public User getOwnerUserOfApartmentById(long apartmentId) {
        long ownerUserId = apartmentRepository.findById(apartmentId).orElse(new Apartment()).getOwnerUser().getId();
        return userRepository.findById(ownerUserId).orElse(new User());
    }

    public void saveMessage(Message message) {
        messageRepository.save(message);
    }

    public String getApartmentTitleByApartmentId(long apartmentId) {
        return apartmentRepository.findById(apartmentId).stream().map(Apartment::getTitle).findFirst().orElse("Nieznany tytul");
    }

    public User getPrincipalUser(String name) {
        return userRepository.findByUserName(name).orElse(new User());
    }

    public List<User> getAllSenderUserByPrincipalAndType(String name, MessageType messageType) {
        User principalUser = userRepository.findByUserName(name).orElse(new User());

        List<Message> allMessages = messageRepository.findAllByReceiverUserIdAndType(principalUser.getId(), messageType);

        Set<Long> userSet = new HashSet<>();

        for (Message allMessage : allMessages) {
            userSet.add(allMessage.getSenderUserId());
        }

        List<User> userList = new ArrayList<>();

        for (Long userId : userSet) {
            userList.add(userRepository.findById(userId).orElse(new User()));
        }

        return userList;
    }

    public List<Message> getMessagesBetweenUsersBySenderAndReceiver(long senderId, String name, MessageType messageType) {
        User receiverUser = userRepository.findByUserName(name).orElse(new User());
        List<Message> first = messageRepository.findAllBySenderUserIdAndReceiverUserIdAndType(receiverUser.getId(),senderId, messageType);
        List<Message> second = messageRepository.findAllBySenderUserIdAndReceiverUserIdAndType(senderId, receiverUser.getId(), messageType);

        List<Message> concat = new ArrayList<>();
        concat.addAll(first);
        concat.addAll(second);

        concat.sort(Comparator.comparing(Message::getDateOfSendMsg));
        return concat ;
    }

    public String getTitleMessages(List<Message> messages) {
        return messages.stream().map(Message::getTitle).findFirst().orElse("Brak tytulu");
    }

    public int getUnReadMessagesSizeByUserIdAndType(long id, MessageType messageType) {
        return messageRepository.countMessagesByReadIsFalseAndUsersIsAndTypeIs(id, messageType);
    }

    public void updateReadMessageWherePrincipalIsAndMessagesIs(String name, List<Message> messages) {
        User principalUser = userRepository.findByUserName(name).orElse(new User());

        messages.stream()
                .filter(x -> x.getReceiverUserId() == principalUser.getId())
                .forEach(x -> x.setRead(true));
        messages.forEach(messageRepository::save);
    }

    public String getSenderUserFullNameByUserId(long userId) {
        return userRepository.findById(userId).orElse(new User()).getFullName();
    }

    public long getApartmentIdByMessages(List<Message> messages) {
        return messages.stream()
                .map(Message::getMsgAboutApartmentId)
                .findFirst().orElse(0L);
    }



    public User getUserById(long senderId) {
        return userRepository.findById(senderId).orElse(new User());
    }


    public boolean checkIsPrincipalUserHaveContactWithOwnerUserAboutApartment(long principalId, long senderId, long apartmentId) {
        int i = messageRepository.checkIsPrincipalUserHaveContactWithOwnerUserAboutApartment(principalId, senderId, apartmentId);
        if (i>0){
            return true;
        }
        return false;
    }
}