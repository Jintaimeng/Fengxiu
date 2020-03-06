package com.meng.missyou.validators;

import com.meng.missyou.dto.PersonDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordEqual, PersonDTO> {
    //第二个：自定义注解修饰的目标的类型
    private int min;
    private int max;

    @Override
    public void initialize(PasswordEqual constraintAnnotation) {
        this.max = constraintAnnotation.max();
        this.min = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(PersonDTO value, ConstraintValidatorContext context) {
        String password1 = value.getPassword1();
        String password2 = value.getPassword2();
        boolean match = password1.equals(password2);
        boolean match2 = false;
        if (password2.length() >= min && password1.length() <= max)
            match2 = true;
        return match && match2;
    }

}
