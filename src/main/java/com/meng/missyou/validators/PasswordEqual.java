package com.meng.missyou.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = PasswordValidator.class)
public @interface PasswordEqual {
    int min() default 3;

    int max() default 8;

    String message() default "Passwords are not equal";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    //关联类

}
