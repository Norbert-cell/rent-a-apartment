package pl.coderslab.rentaapartment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.coderslab.rentaapartment.model.Message;
import pl.coderslab.rentaapartment.model.MessageType;
import pl.coderslab.rentaapartment.model.User;

import java.io.Serializable;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long>, PagingAndSortingRepository<Message,Long> {


    List<Message> findAllByReceiverUserIdAndType(long receiverId, MessageType messageType);

    List<Message> findAllBySenderUserIdAndReceiverUserIdAndType(long senderId, long receiverId, MessageType messageType);

    @Query("select count(m) from Message m where m.receiverUserId=:userId and m.type=:type and m.isRead=false")
    int countMessagesByReadIsFalseAndUsersIsAndTypeIs(@Param("userId") long userId, @Param("type") MessageType type);

}
