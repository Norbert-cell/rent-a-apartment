package pl.coderslab.rentaapartment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.rentaapartment.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);

    Optional<User> findByEmail(String email);
}
