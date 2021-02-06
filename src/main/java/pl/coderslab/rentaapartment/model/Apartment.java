package pl.coderslab.rentaapartment.model;


import pl.coderslab.rentaapartment.validator.ApartmentValidationGroup;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Size(min = 5, max = 124,groups = {ApartmentValidationGroup.class})
    @NotBlank(groups = {ApartmentValidationGroup.class})
    private String title;
    @NotBlank(groups = {ApartmentValidationGroup.class})
    @Column(length = 10000, columnDefinition = "TEXT")
    @Size(min=20, max = 100000, groups ={ApartmentValidationGroup.class} )
    private String content;
    @NotNull(groups = {ApartmentValidationGroup.class})
    @Min(value = 1,groups = {ApartmentValidationGroup.class})
    private int apartmentArea;
    @NotNull(groups = {ApartmentValidationGroup.class})
    @Min(value = 1,groups = {ApartmentValidationGroup.class})
    private int rooms;
    @NotNull(groups = {ApartmentValidationGroup.class})
    @Min(value = 1,groups = {ApartmentValidationGroup.class})
    private Double price;
    private LocalDateTime created;
    private LocalDateTime updated;
    private boolean rented;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Valid
    private Address address;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private User ownerUser;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User tenantUser;
    @Min(value = 1, groups = {ApartmentValidationGroup.class})
    private Double myBills;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Double getMyBills() {
        return myBills;
    }

    public void setMyBills(Double myBills) {
        this.myBills = myBills;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

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

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
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

    public User getOwnerUser() {
        return ownerUser;
    }

    public void setOwnerUser(User ownerUser) {
        this.ownerUser = ownerUser;
    }

    public User getTenantUser() {
        return tenantUser;
    }

    public void setTenantUser(User tenantUser) {
        this.tenantUser = tenantUser;
    }


    @Override
    public String toString() {
        return "Apartment{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", apartmentArea=" + apartmentArea +
                ", rooms=" + rooms +
                ", price=" + price +
                ", created=" + created +
                ", updated=" + updated +
                ", rented=" + rented +
                '}';
    }
}
