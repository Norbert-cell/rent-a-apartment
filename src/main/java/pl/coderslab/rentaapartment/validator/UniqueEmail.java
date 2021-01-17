package pl.coderslab.rentaapartment.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = UniqueEmailValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UniqueEmail {
    String message() default "Podany email istnieje w naszym serwisie!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
