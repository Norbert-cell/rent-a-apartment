package pl.coderslab.rentaapartment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.rentaapartment.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
