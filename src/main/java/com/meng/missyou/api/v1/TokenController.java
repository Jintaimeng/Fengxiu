package com.meng.missyou.api.v1;

import com.meng.missyou.dto.TokenGetDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "token")
public class TokenController {
    @PostMapping("")
    public Map<String, String> getToken(@RequestBody @Validated TokenGetDTO userData) {
//        Map<String, String> map = new HashMap<>();
//        String token = null;
//        switch (userData.getLoginType()){
//            case USER_WX:
//                break;
//            case USER_Email:
//                break;
//            default: throw new NotFoundException(10003);
//        }
        return null;
    }
}
