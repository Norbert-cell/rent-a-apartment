package pl.coderslab.rentaapartment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.rentaapartment.model.Message;
import pl.coderslab.rentaapartment.model.MessageType;
import pl.coderslab.rentaapartment.model.User;

import java.io.Serializable;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long>, PagingAndSortingRepository<Message,Long> {

//    List<Message> findAllByTypeAndReceiverUser(MessageType type, long receiverUser);
//
//    List<Message> findAllBySenderUserAndReceiverUser(long sender, long receiver);
//
//    List<Message> findAllByReceiverUserAndTypeOrderByDateOfSendMsgDesc(long id, MessageType messageType);
//
//    List<Message> findAllByReceiverUserAndSenderUserAndTypeOrderByDateOfSendMsgDesc(long receiverId, long senderId, MessageType messageType);
//
//    List<Message> findAllBySenderUserAndReceiverUserAndTypeOrderByDateOfSendMsgDesc(long senderId, long receiverId, MessageType messageType);
//
//    List<Message> findAllByReceiverUserAndType(long receiverId, MessageType messageType);

////    @Query(value="select * from Message m where m.users = ?1 and m.type = ?2",nativeQuery=true)
//    List<Message> findAllByUsersAndTypeAndMsgAboutApartmentId(List<User> users, MessageType messageType, long apartmentId);

    @Query(value="select * from Message inner join Message_User MU on Message.id = MU.Message_id\n" +
            "where MU.users_id = ?1 and type =?2", nativeQuery=true)
    List<Message> findAllMessagesByUserIdAndType(long userId, int type);

    @Query(value="select count(*) from Message inner join Message_User MU on Message.id = MU.Message_id where users_id=?1 and type =?2 and Message.isRead = false"
            ,nativeQuery=true)
    int countMessagesByReadIsFalseAndUsersIsAndTypeIs(long userId, int type);

    List<Message> findAllById(long messageId);

    @Query(value="select Message_id from Message inner join Message_User MU on Message.id = MU.Message_id where MU.users_id=?1",
    nativeQuery=true)
    List<Long> getMessagesIdByUser(long userId);

    @Query(value="select * from Message inner join Message_User MU on Message.id = MU.Message_id\n" +
            "where users_id =?1 and msgAboutApartmentId=?2 and type = 1"
    , nativeQuery= true)
    List<Message> findAllByMsgAboutApartmentIdAndUsersAndTypeIsNormal(long senderId, long apartmentId);

    @Query(value="select * from Message inner join Message_User MU on Message.id = MU.Message_id where msgAboutApartmentId=?1 and users_id=?2 or users_id=?1 limit 1", nativeQuery=true)
    Message findByMsgAboutApartmentIdAndUsers(long apartmentId, long senderId, long receiverId);

    @Query(value="select * from Message inner join Message_User MU on Message.id = MU.Message_id\n" +
            "where users_id =?1 and msgAboutApartmentId=?2 and type = 0"
            , nativeQuery= true)
    List<Message> findAllByMsgAboutApartmentIdAndUsersAndTypeIsFault(long senderId, long apartmentId);
}
