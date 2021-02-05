package pl.coderslab.rentaapartment.model;

import org.hibernate.validator.constraints.pl.NIP;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.coderslab.rentaapartment.validator.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(groups = {FirmValidationGroup.class, UserValidationGroup.class})
    @UniqueEmail(groups = {FirmValidationGroup.class, UserValidationGroup.class})
    @Column(name = "email")
    @Pattern(regexp = "[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.([a-zA-Z]{2,}){1}",
            groups = {FirmValidationGroup.class, UserValidationGroup.class})
    private String userName;

    @NotBlank(groups = {UserValidationGroup.class, EditUserValidationGroup.class})
    @Column(name = "firstName")
    @Pattern(regexp = "^[a-zA-Z]{2,15}$", groups = {UserValidationGroup.class})
    private String firstName;

    @NotBlank(groups = {FirmValidationGroup.class, EditFirmValidationGroup.class})
    private String firmName;

    @NotBlank(groups = {UserValidationGroup.class,EditUserValidationGroup.class})
    @Column(name = "lastName")
    @Pattern(regexp = "^[a-zA-Z]{2,15}$",groups = {UserValidationGroup.class,EditUserValidationGroup.class})
    private String lastName;

    @NotBlank(groups = {FirmValidationGroup.class, UserValidationGroup.class})
    @Size(min = 3, max = 48,groups = {FirmValidationGroup.class, UserValidationGroup.class})
    private String password;

    private LocalDateTime created;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "tenantUser", cascade = CascadeType.REMOVE)
    private Apartment rentedHouse;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "ownerUser", cascade = CascadeType.REMOVE)
    private List<Apartment> auctions;

    @NotBlank(groups = {FirmValidationGroup.class})
    @NIP(groups = {FirmValidationGroup.class})
    private String nip;

    @REGON(groups = {FirmValidationGroup.class})
    @NotBlank(groups =
            FirmValidationGroup.class)
    private String regon;

    private boolean isEnabled;

    public User() {
    }

    public User(long id, @NotBlank String userName, @NotBlank String firstName, @NotBlank String lastName, @NotBlank String password) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public String getFullName(){
        if (role == Role.ROLE_FIRM){
            return this.firmName;
        }
        return this.firstName +" " + this.lastName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role.getAuthority()));
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
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getFirmName() {
        return firmName;
    }

    public void setFirmName(String firmName) {
        this.firmName = firmName;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Apartment getRentedHouse() {
        return rentedHouse;
    }

    public void setRentedHouse(Apartment rentedHouse) {
        this.rentedHouse = rentedHouse;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getRegon() {
        return regon;
    }

    public void setRegon(String regon) {
        this.regon = regon;
    }

    public List<Apartment> getAuctions() {
        return auctions;
    }

    public void setAuctions(List<Apartment> auctions) {
        this.auctions = auctions;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", firmName='" + firmName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", created=" + created +
                ", role=" + role +
                ", rentedHouse=" + rentedHouse +
                ", auctions=" + auctions +
                ", nip='" + nip + '\'' +
                ", regon='" + regon + '\'' +
                ", isEnabled=" + isEnabled +
                '}';
    }
}
