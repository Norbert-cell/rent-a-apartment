package pl.coderslab.rentaapartment.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = uniqueEmailValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface uniqueEmail {
    String message() default "{Podany email juz istnieje w naszym serwisie!}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
