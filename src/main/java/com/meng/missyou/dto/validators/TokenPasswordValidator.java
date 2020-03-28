package com.meng.missyou.dto.validators;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TokenPasswordValidator implements ConstraintValidator<TokenPassword, String> {
    //泛型：第一个是注解的名，第二个是注解打在类或字段上，类或字段的类型
    private Integer min;
    private Integer max;

    @Override
    public void initialize(TokenPassword constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {//小程序是允许不填写password的
            return true;
        }
        return value.length() >= this.min && value.length() <= this.max;
    }
}
