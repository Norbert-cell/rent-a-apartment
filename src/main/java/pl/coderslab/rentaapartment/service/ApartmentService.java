package pl.coderslab.rentaapartment.service;

import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.rentaapartment.model.Apartment;
import pl.coderslab.rentaapartment.model.User;
import pl.coderslab.rentaapartment.repository.ApartmentRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ApartmentService {

    private ApartmentRepository apartmentRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public ApartmentService(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    public void saveApartment(Apartment apartment){

        apartmentRepository.save(apartment);
    }

    public Page<Apartment> findAllByTenantUserIsNull(Pageable page) {
        return apartmentRepository.findAllApartmentsByTenantUserIsNull(page);
    }

    public Boolean findApartmentByUserToCheckIsOwnerUser(User user, Optional<Apartment> apartmentToEdit){
        return apartmentRepository.findAllByOwnerUser(user).stream()
                .anyMatch(x-> x.getOwnerUser() == apartmentToEdit.map(Apartment::getOwnerUser).orElse(new User()));
    }

    public Optional<Apartment> findById(long apartmentId) {
        return apartmentRepository.findById(apartmentId);
    }

    public boolean checkIsRentedApartment(Optional<Apartment> apartment) {
        return apartment.stream().anyMatch(Apartment::isRented);
    }


    public Apartment getOne(long id) {
        return apartmentRepository.getOne(id);
    }


    public boolean checkIsOwnerUser(User user, long apartmentId) {
        List<Apartment> auctions = user.getAuctions();
        boolean isExist = auctions.stream()
                .anyMatch(x -> x.getOwnerUser() == user);
        return isExist;
    }

    public boolean remove(long apartmentId) throws NotFoundException {
        Apartment apartment = apartmentRepository.findById(apartmentId).orElseThrow(() -> new NotFoundException("Nie znaleziono"));

        if (apartment.getTenantUser() == null) {
            apartmentRepository.delete(apartment);
            return true;
        }
        return false;
    }
}
