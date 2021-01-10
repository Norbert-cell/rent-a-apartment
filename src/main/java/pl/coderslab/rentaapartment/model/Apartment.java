package pl.coderslab.rentaapartment.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String content;
    private int apartmentArea;
    private int rooms;
    private Date created;
    private Date updated;
    private boolean rented;
    @ManyToOne
    private Address address;
    @OneToOne
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getApartmentArea() {
        return apartmentArea;
    }

    public void setApartmentArea(int apartmentArea) {
        this.apartmentArea = apartmentArea;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public boolean isRented() {
        return rented;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", apartmentArea=" + apartmentArea +
                ", rooms=" + rooms +
                ", created=" + created +
                ", updated=" + updated +
                ", rented=" + rented +
                ", address=" + address +
                ", user=" + user +
                '}';
    }
}
