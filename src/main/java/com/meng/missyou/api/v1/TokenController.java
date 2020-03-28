package com.meng.missyou.api.v1;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "token")
public class TokenController {
    @PostMapping("")
    public void getToken(@RequestBody @Validated TokenGetDTO userData) {

    }
}
