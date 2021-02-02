package pl.coderslab.rentaapartment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.rentaapartment.model.Apartment;
import pl.coderslab.rentaapartment.model.User;

import java.util.List;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment,Long>, PagingAndSortingRepository<Apartment,Long> {

    List<Apartment> findAllByOwnerUser(User user);

    Page<Apartment> findAllApartmentsByTenantUserIsNull(Pageable page);

    Page<Apartment> findAllByOwnerUserAndTenantUserIsNull(User user, Pageable page);

    Page<Apartment> findAllByOwnerUserAndRentedIsTrue(User user, Pageable page);

    Apartment findApartmentByTenantUser(User userToCheckIsrentedApartmentbyhim);

    List<Apartment> findAllByOwnerUserAndRentedIsFalse(User user);

}
