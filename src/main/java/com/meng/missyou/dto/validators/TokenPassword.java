package com.meng.missyou.dto.validators;

import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
//@Constraint(validatedBy = PasswordValidator.class)
public @interface TokenPassword {
    int min() default 6;

    int max() default 32;

    String message() default "字段不符合要求";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
