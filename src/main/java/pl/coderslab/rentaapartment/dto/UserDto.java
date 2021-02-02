package pl.coderslab.rentaapartment.dto;

import org.hibernate.validator.constraints.pl.NIP;
import org.hibernate.validator.constraints.pl.REGON;
import pl.coderslab.rentaapartment.model.Role;
import pl.coderslab.rentaapartment.validator.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class UserDto {

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

    @NotBlank(groups = {UserValidationGroup.class})
    @Column(name = "lastName")
    @Pattern(regexp = "^[a-zA-Z]{2,15}$",groups = {UserValidationGroup.class})
    private String lastName;

    @NotBlank(groups = {FirmValidationGroup.class, UserValidationGroup.class})
    @Size(min = 3, max = 48,groups = {FirmValidationGroup.class, UserValidationGroup.class})
    private String password;

    private LocalDateTime created;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @NotBlank(groups = {FirmValidationGroup.class})
    @NIP(groups = {FirmValidationGroup.class})
    private String nip;

    @REGON(groups = {FirmValidationGroup.class})
    @NotBlank(groups =
            FirmValidationGroup.class)
    private String regon;

    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFirmName() {
        return firmName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public Role getRole() {
        return role;
    }

    public String getNip() {
        return nip;
    }

    public String getRegon() {
        return regon;
    }
}
