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

//    @Query(value="select * from Message inner join Message_User MU on Message.id = MU.Message_id\n" +
//            "where MU.users_id = ?1 and type like ?2", nativeQuery=true)
//    List<Message> findAllMessagesByUserIdAndType(long userId, MessageType type);

    List<Message> findAllByReceiverUserIdAndType(long receiverId, MessageType messageType);

    List<Message> findAllBySenderUserIdAndReceiverUserIdAndType(long senderId, long receiverId, MessageType messageType);

    @Query("select count(m) from Message m where m.receiverUserId=:userId and m.type=:type and m.isRead=false")
    int countMessagesByReadIsFalseAndUsersIsAndTypeIs(@Param("userId") long userId, @Param("type") MessageType type);

    @Query(value ="select count(id) from Message where receiverUserId = ?1 and senderUserId = ?2 and msgAboutApartmentId=?3 limit 1", nativeQuery = true)
    int checkIsPrincipalUserHaveContactWithOwnerUserAboutApartment(long principalId, long senderId, long apartmentId);

}
