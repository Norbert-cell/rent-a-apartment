package pl.coderslab.rentaapartment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.rentaapartment.model.Apartment;

public interface ApartmentRepository extends JpaRepository<Apartment,Long> {
}
