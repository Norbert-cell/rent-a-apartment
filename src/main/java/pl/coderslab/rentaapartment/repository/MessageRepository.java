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

    @Query(value="select * from Message inner join Message_User MU on Message.id = MU.Message_id\n" +
            "where MU.users_id = ?1 and type =?2", nativeQuery=true)
    List<Message> findAllMessagesByUserIdAndType(long userId, MessageType type);

    List<Message> findAllByReceiverUserIdAndType(long receiverId, MessageType messageType);

    List<Message> findAllBySenderUserIdAndReceiverUserIdAndType(long senderId, long receiverId, MessageType messageType);



    @Query(value="select COUNT(*) from Message where receiverUserId = ?1 and type=?2 and isRead=false;"
            ,nativeQuery=true)
    int countMessagesByReadIsFalseAndUsersIsAndTypeIs(long userId, int type);

}
