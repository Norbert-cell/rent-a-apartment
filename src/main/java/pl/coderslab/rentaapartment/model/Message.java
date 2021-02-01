package pl.coderslab.rentaapartment.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String content;
    private Long senderUser;
    private Long receiverUser;
    private LocalDateTime dateOfSendMsg;
    private double estimatedPrice;
    private Long msgAboutApartmentId;
    private MessageType type;
    private boolean isRead;
    @ManyToMany
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public Long getMsgAboutApartmentId() {
        return msgAboutApartmentId;
    }

    public void setMsgAboutApartmentId(Long msgAboutApartmentId) {
        this.msgAboutApartmentId = msgAboutApartmentId;
    }

    public double getEstimatedPrice() {
        return estimatedPrice;
    }

    public void setEstimatedPrice(double estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getSenderUser() {
        return senderUser;
    }

    public void setSenderUser(Long senderUser) {
        this.senderUser = senderUser;
    }

    public Long getReceiverUser() {
        return receiverUser;
    }

    public void setReceiverUser(Long receiverUser) {
        this.receiverUser = receiverUser;
    }

    public LocalDateTime getDateOfSendMsg() {
        return dateOfSendMsg;
    }

    public void setDateOfSendMsg(LocalDateTime dateOfSendMsg) {
        this.dateOfSendMsg = dateOfSendMsg;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", senderUser=" + senderUser +
                ", receiverUser=" + receiverUser +
                ", dateOfSendMsg=" + dateOfSendMsg +
                ", estimatedPrice=" + estimatedPrice +
                ", msgAboutApartmentId=" + msgAboutApartmentId +
                ", type=" + type +
                ", isRead=" + isRead +
                '}';
    }
}
