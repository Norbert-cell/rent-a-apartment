package pl.coderslab.rentaapartment.validator;

import org.springframework.beans.factory.annotation.Autowired;
import pl.coderslab.rentaapartment.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class uniqueEmailValidator implements ConstraintValidator<uniqueEmail, String> {

    @Autowired
    private UserService userService;

    public uniqueEmailValidator() {
    }


    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return email != null && !userService.isEmailExist(email);
    }


}

