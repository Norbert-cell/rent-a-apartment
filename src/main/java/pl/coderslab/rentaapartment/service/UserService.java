package pl.coderslab.rentaapartment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.rentaapartment.model.User;
import pl.coderslab.rentaapartment.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;


    public UserService(UserRepository userRepository, EntityManager entityManager) {
        this.userRepository = userRepository;
        this.entityManager = entityManager;
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

}
