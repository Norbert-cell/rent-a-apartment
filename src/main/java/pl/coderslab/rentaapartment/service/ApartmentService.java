package pl.coderslab.rentaapartment.service;

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

    public List<Apartment> findAll() {
        return apartmentRepository.findAll();
    }

    public List<Apartment> findApartmentByUser(User user){
        return apartmentRepository.findAllByOwnerUser(user);
    }

    public Optional<Apartment> findById(long apartmentId) {
        return apartmentRepository.findById(apartmentId);
    }

    public List<Apartment> userActiveAuctions(User user){
        return entityManager.createQuery("select a from Apartment a where a.ownerUser=:user and a.rented = false")
                .setParameter("user",user)
                .getResultList();
    }

    public List<Apartment> userRentedApartment(User user) {
        return entityManager.createQuery("select a from Apartment a where a.ownerUser=:user and a.rented = true")
                .setParameter("user",user)
                .getResultList();
    }

    public List<Apartment> findRentedApartmentsByUser(User user) {
       return entityManager.createQuery("select a from Apartment a where a.rented=true and a.ownerUser=:user")
                .setParameter("user",user)
                .getResultList();
    }
}
