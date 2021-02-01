package pl.coderslab.rentaapartment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.rentaapartment.model.Message;
import pl.coderslab.rentaapartment.model.MessageType;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long>, PagingAndSortingRepository<Message,Long> {

    List<Message> findAllByTypeAndReceiverUser(MessageType type, long receiverUser);

    List<Message> findAllBySenderUserAndReceiverUser(long sender, long receiver);

    List<Message> findAllByReceiverUserAndTypeOrderByDateOfSendMsgDesc(long id, MessageType messageType);

    List<Message> findAllByReceiverUserAndSenderUserAndTypeOrderByDateOfSendMsgDesc(long receiverId, long senderId, MessageType messageType);

    List<Message> findAllBySenderUserAndReceiverUserAndTypeOrderByDateOfSendMsgDesc(long senderId, long receiverId, MessageType messageType);

    List<Message> findAllByReceiverUserAndType(long receiverId, MessageType messageType);

}
