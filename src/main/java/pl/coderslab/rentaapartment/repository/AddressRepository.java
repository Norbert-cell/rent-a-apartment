package pl.coderslab.rentaapartment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.rentaapartment.model.Address;
import pl.coderslab.rentaapartment.service.AddressService;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
