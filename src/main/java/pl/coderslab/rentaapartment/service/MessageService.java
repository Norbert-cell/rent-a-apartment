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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class MessageService {

    private final MessageRepository messageRepository;
    private final ApartmentRepository apartmentRepository;
    private final UserRepository userRepository;
    @PersistenceContext
    private EntityManager entityManager;

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


    public boolean findMyrentedApartmentByUserNameAndApartmentId(String name, long apartmentId) throws NotFoundException {
        User userToCheckIsrentedApartmentbyhim = userRepository.findByUserName(name).orElseThrow(() -> new NotFoundException("Nie ma uzytkownika"));
        Apartment apartmentByTenantUser = apartmentRepository.findApartmentByTenantUser(userToCheckIsrentedApartmentbyhim);
        return apartmentByTenantUser.getId() == apartmentId;
    }

    public String getApartmentTitleByApartmentId(long apartmentId) {
        return apartmentRepository.findById(apartmentId).stream().map(Apartment::getTitle).findFirst().orElse("Nieznany tytul");
    }

    public User getPrincipalUser(String name) {
        return userRepository.findByUserName(name).orElse(new User());
    }

    public Set<User> getAllSenderUserByPrincipalAndType(String name, MessageType messageType) {
        int type = 0;
        if (messageType.equals(MessageType.NORMAL_MESSAGE)) {
            type = 1;
        }
        User principalUser = userRepository.findByUserName(name).orElse(new User());
        List<Message> allMessagesBeetweenUser = messageRepository.findAllMessagesByUserIdAndType(principalUser.getId(), type);
        Set<User> senderUsers = new HashSet<>();
        for (Message message : allMessagesBeetweenUser) {
            List<User> users = message.getUsers();
            for (User user : users) {
                if (user.getId() != principalUser.getId()) {
                    senderUsers.add(user);
                }
            }
        }
        return senderUsers;
    }

    public Set<Message> getMessagesBetweenUsersBySenderAndReceiver(long senderId, String name, MessageType messageType) {

        User principalUser = userRepository.findByUserName(name).orElse(new User());
        List<Long> messagesByPrincipal = messageRepository.getMessagesIdByUser(principalUser.getId());
        List<Long> messageBySender = messageRepository.getMessagesIdByUser(senderId);

        List<Long> finalLong = new ArrayList<>();

        for (Long aLong : messageBySender) {
            for (Long aLong1 : messagesByPrincipal) {
                if (aLong == aLong1) {
                    finalLong.add(aLong);
                }
            }
        }

        long messageId = finalLong.get(0);

        List<Message> messagesBetweenUser = messageRepository.findAllById(messageId);

        List<Message> messages;

        long apartmentId = getApartmentIdByMessages(messagesBetweenUser);
        if (messageType.equals(MessageType.FAULT_MESSAGE)) {
            messages = messageRepository.findAllByMsgAboutApartmentIdAndUsersAndTypeIsFault(senderId, apartmentId);
        } else {
            messages = messageRepository.findAllByMsgAboutApartmentIdAndUsersAndTypeIsNormal(senderId, apartmentId);
        }

        List<Message> finalMessage = new ArrayList<>();
        for (Message message : messages) {
            for (Long aLong : finalLong) {
                if (message.getId() == aLong) {
                    finalMessage.add(message);
                }
            }
        }

        Set<Message> collect = finalMessage.stream()
                .collect(Collectors.toSet());

        return collect;
    }

    public String getTitleMessages(Set<Message> messages) {
        return messages.stream().map(Message::getTitle).findFirst().orElse("Brak tytulu");
    }

    public int getUnReadMessagesSizeByUserIdAndType(long id, MessageType messageType) {
        int type = 0;
        if (messageType.equals(MessageType.NORMAL_MESSAGE)) {
            type = 1;
        }
        return messageRepository.countMessagesByReadIsFalseAndUsersIsAndTypeIs(id, type);
    }

    public void updateReadMessageWherePrincipalIsAndMessageId(String name, Set<Message> messages) {
        User principalUser = userRepository.findByUserName(name).orElse(new User());
        for (Message message : messages) {
            for (User user : message.getUsers()) {
                if (principalUser == user) {
                    message.setRead(true);
                }
            }
        }
        messages.forEach(messageRepository::save);
    }

    public String getSenderUserFullNameByUserId(long userId) {
        return userRepository.findById(userId).orElse(new User()).getFullName();
    }

    public Message findById(long messageId) {
        return messageRepository.findById(messageId).orElse(new Message());
    }

    public long getApartmentIdByMessages(List<Message> messages) {
        return messages.stream()
                .map(Message::getMsgAboutApartmentId)
                .findFirst().orElse(0L);
    }

    public long getApartmentIdByMessagesSet(Set<Message> messages) {
        return messages.stream()
                .map(Message::getMsgAboutApartmentId)
                .findFirst().orElse(0L);
    }

    public Message findMessageBetweenOwnerAndPrincipalByMsgAboutApartmentIdAndUsers(long msgAboutApartmentId, User ownerUserOfApartmentById, User principalUser) {
        Message msg = messageRepository.findByMsgAboutApartmentIdAndUsers(msgAboutApartmentId, principalUser.getId(), ownerUserOfApartmentById.getId());
        if (msg == null) {
            return new Message();
        }
        return msg;
    }

    public List<Message> convertSetToListAndSortByDate(Set<Message> messages) {
        List<Message> messageList = messages.stream()
                .collect(Collectors.toList());
        messageList.sort(Comparator.comparing(Message::getDateOfSendMsg));
        return messageList;
    }

    public List<User> convertSetToListAndSortByRead(Set<User> senderUser) {
        List<User> collect = senderUser.stream().collect(Collectors.toList());
        collect.sort(Comparator.comparing(User::getId));
        return collect;
    }

}