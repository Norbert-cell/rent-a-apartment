package pl.coderslab.rentaapartment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.rentaapartment.dao.UserDaoImpl;
import pl.coderslab.rentaapartment.model.User;
import pl.coderslab.rentaapartment.repository.UserRepository;

import java.util.Optional;

@Service
@Transactional
public class UserService{

    private UserRepository userRepository;
    private UserDaoImpl userDao;
    private PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void persistUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        logger.info(user.toString());
        userDao.saveUser(user);
    }

    public void mergeUser(User user) {
        userRepository.save(user);
    }

    public void removeUser(long userId){
        User user = userRepository.findById(userId).orElseThrow();
        userRepository.delete(user);
    }

    public UserDetails findByUserName(String userName) {
        return userRepository.findByUserName(userName).orElseThrow();
    }

    public boolean isEmailExist(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }
}
