package pl.coderslab.rentaapartment.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.coderslab.rentaapartment.validator.uniqueEmail;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    private String userName;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @uniqueEmail
    private String email;
    @NotBlank
    private String password;
    private LocalDateTime created;
    private String role;
    @OneToOne
    private Apartment rentedHouse;
    @ManyToMany
    private List<Apartment> observedApartments;
    @OneToMany
    private List<Apartment> auctions;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Apartment getRentedHouse() {
        return rentedHouse;
    }

    public void setRentedHouse(Apartment rentedHouse) {
        this.rentedHouse = rentedHouse;
    }

    public List<Apartment> getObservedApartments() {
        return observedApartments;
    }

    public void setObservedApartments(List<Apartment> observedApartments) {
        this.observedApartments = observedApartments;
    }

    public List<Apartment> getAuctions() {
        return auctions;
    }

    public void setAuctions(List<Apartment> auctions) {
        this.auctions = auctions;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", created=" + created +
                ", role='" + role + '\'' +
                ", rentedHouse=" + rentedHouse +
                ", observedApartments=" + observedApartments +
                ", auctions=" + auctions +
                '}';
    }
}
