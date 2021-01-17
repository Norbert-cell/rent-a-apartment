package pl.coderslab.rentaapartment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.rentaapartment.model.Apartment;
import pl.coderslab.rentaapartment.model.User;

import java.util.List;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment,Long> {

    List<Apartment> findAllByOwnerUser(User user);


}
