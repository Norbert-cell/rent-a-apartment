package pl.coderslab.rentaapartment.dto;

import pl.coderslab.rentaapartment.model.Role;

import java.time.LocalDateTime;

public class UserDto {

    private long id;
    private String userName;
    private String firstName;
    private String firmName;
    private String lastName;
    private LocalDateTime created;
    private Role role;
    private String nip;
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

    public void setId(long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setFirmName(String firmName) {
        this.firmName = firmName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public void setRegon(String regon) {
        this.regon = regon;
    }
}
