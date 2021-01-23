package pl.coderslab.rentaapartment.controller;

import org.junit.jupiter.api.Test;
import pl.coderslab.rentaapartment.model.Address;
import pl.coderslab.rentaapartment.model.Apartment;
import pl.coderslab.rentaapartment.model.Role;
import pl.coderslab.rentaapartment.model.User;
import pl.coderslab.rentaapartment.service.AddressService;
import pl.coderslab.rentaapartment.service.ApartmentService;
import pl.coderslab.rentaapartment.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

class UserControllerTest {

    private UserService userService;
    private ApartmentService apartmentService;
    private AddressService addressService;

    UserControllerTest(UserService userService, ApartmentService apartmentService, AddressService addressService) {
        this.userService = userService;
        this.apartmentService = apartmentService;
        this.addressService = addressService;
    }


    @Test
    void shouldReturnAllUserApartmentsWithoutSomeoneElseUserApartments() {
        //given

        User user = new User();
        user.setUserName("test@wp.pl");
        user.setRole(Role.ROLE_USER);
        user.setFirstName("testFirstName");
        user.setLastName("testLastName");
        user.setCreated(LocalDateTime.now());
        user.setEnabled(true);

        Address testAddress = new Address();
        testAddress.setStreet("TestStreet");
        testAddress.setStreetNumber("22");
        testAddress.setPostCode("03-333");
        testAddress.setCity("TestCity");

        Apartment apartment = new Apartment();
        apartment.setOwnerUser(user);
        apartment.setCreated(LocalDateTime.now());
        apartment.setPrice(3000.0);
        apartment.setMyBills(2000.0);
        apartment.setRented(false);
        apartment.setRooms(3);
        apartment.setApartmentArea(55);
        apartment.setContent("TestOpisTestOpisTestOpisTestOpisTestOpisTestOpisTestOpisTestOpis");
        apartment.setAddress(testAddress);

        addressService.saveAddress(testAddress);
        apartmentService.saveApartment(apartment);
        userService.saveUser(user);

        //when

        List<Apartment> apartmentByUser = apartmentService.findApartmentByUser(user);
        int apartmentSize = apartmentByUser.size();

        //then

        assertEquals(1,apartmentSize);
    }

    @Test
    void editUser() {
    }

    @Test
    void edit() {
    }

    @Test
    void initaddApartment() {
    }

    @Test
    void addApartment() {
    }

    @Test
    void rentedApartment() {
    }

    @Test
    void countEarning() {
    }
}