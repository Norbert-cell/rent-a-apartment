package pl.coderslab.rentaapartment.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.coderslab.rentaapartment.model.User;
import pl.coderslab.rentaapartment.repository.UserRepository;

@Component
public class UserConverter implements Converter<String, User> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User convert(String s) {
        return userRepository.findById(Long.parseLong(s)).orElseThrow();
    }
}
