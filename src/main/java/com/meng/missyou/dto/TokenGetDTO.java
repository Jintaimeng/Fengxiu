package com.meng.missyou.dto;

import com.meng.missyou.core.enumeration.LoginType;
import com.meng.missyou.dto.validators.TokenPassword;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TokenGetDTO {
    @NotBlank(message = "account不允许为空")
    private String account;

    @TokenPassword(max = 30, message = "{token.password}")
    private String password;

    private LoginType loginType;

}
