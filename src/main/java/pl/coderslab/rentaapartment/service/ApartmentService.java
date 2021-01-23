package pl.coderslab.rentaapartment.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.rentaapartment.model.Apartment;
import pl.coderslab.rentaapartment.model.User;
import pl.coderslab.rentaapartment.repository.ApartmentRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    public List<Apartment> findApartmentByUser(User user){
        return apartmentRepository.findAllByOwnerUser(user);
    }

    public Optional<Apartment> findById(long apartmentId) {
        return apartmentRepository.findById(apartmentId);
    }

//    public List<Apartment> userActiveAuctions(User user){
//        return entityManager.createQuery("select a from Apartment a where a.ownerUser=:user and a.rented = false")
//                .setParameter("user",user)
//                .getResultList();
//    }

//    public List<Apartment> userRentedApartment(User user) {
//        return entityManager.createQuery("select a from Apartment a where a.ownerUser=:user and a.rented = true")
//                .setParameter("user",user)
//                .getResultList();
//    }

    public Page<Apartment> userActiveAuctions(User user, Pageable page){
        return apartmentRepository.findAllByOwnerUserAndTenantUserIsNull(user,page);
    }

    public Page<Apartment> userRentedApartment(User user, Pageable page) {
        return apartmentRepository.findAllByOwnerUserAndRentedIsTrue(user,page);
    }

}
