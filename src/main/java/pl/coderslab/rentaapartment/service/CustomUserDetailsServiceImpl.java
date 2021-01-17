package pl.coderslab.rentaapartment.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.rentaapartment.model.User;
import pl.coderslab.rentaapartment.repository.UserRepository;


@Service
@Transactional
@Qualifier("CustomUserDetailsServiceImpl")
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = null;
        try {
            user = userRepository.findByUserName(email).orElseThrow(()->new NotFoundException("Nie znaleziono"));
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return user ;
    }
}
