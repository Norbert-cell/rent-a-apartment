package pl.coderslab.rentaapartment.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private LocalDateTime dateOfSendMsg;
    private double estimatedPrice;
    private Long msgAboutApartmentId;
    @Enumerated(EnumType.STRING)
    private MessageType type;
    private boolean isRead;
    private long senderUserId;
    private long receiverUserId;

    public long getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(long senderUserId) {
        this.senderUserId = senderUserId;
    }

    public long getReceiverUserId() {
        return receiverUserId;
    }

    public void setReceiverUserId(long receiverUserId) {
        this.receiverUserId = receiverUserId;
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
                ", dateOfSendMsg=" + dateOfSendMsg +
                ", estimatedPrice=" + estimatedPrice +
                ", msgAboutApartmentId=" + msgAboutApartmentId +
                ", type=" + type +
                ", isRead=" + isRead +
                '}';
    }
}
