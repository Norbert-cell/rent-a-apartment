package pl.coderslab.rentaapartment.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.rentaapartment.model.Apartment;
import pl.coderslab.rentaapartment.model.Role;
import pl.coderslab.rentaapartment.model.User;
import pl.coderslab.rentaapartment.repository.ApartmentRepository;
import pl.coderslab.rentaapartment.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;
    private ApartmentRepository apartmentRepository;

    @PersistenceContext
    private EntityManager entityManager;


    public UserService(UserRepository userRepository, ApartmentRepository apartmentRepository) {
        this.userRepository = userRepository;
        this.apartmentRepository = apartmentRepository;
    }

    public boolean isExistEmail(String email) {
        Optional<User> byUserName = userRepository.findByUserName(email);
        return byUserName.isPresent();
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public Optional<User> findByUserName(String name) {
        return userRepository.findByUserName(name);
    }

    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    public List<Apartment> findApartmentsByUser(User user) {
        return entityManager.createQuery("select a from Apartment a where a.ownerUser = :user").setParameter("user",user)
                .getResultList();
    }

    public Page<Apartment> userRentedApartment(User user, Pageable paging) {
        return apartmentRepository.findAllByOwnerUserAndRentedIsTrue(user, paging);
    }

    public Page<Apartment> userActiveAuctions(User user, Pageable paging) {
        return apartmentRepository.findAllByOwnerUserAndTenantUserIsNull(user,paging);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void changeRole(User user) {
        Role role = user.getRole();
        if (role.equals(Role.ROLE_USER)){
            entityManager.createQuery("update User set role = 'ROLE_ADMIN' where id=:userId")
                    .setParameter("userId",user.getId())
            .executeUpdate();
        } else {
            entityManager.createQuery("update User set role='ROLE_USER' where id=:userId")
                    .setParameter("userId",user.getId())
            .executeUpdate();
        }
    }
}
