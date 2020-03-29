package com.meng.missyou.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meng.missyou.exception.http.ParameterException;
import com.meng.missyou.model.User;
import com.meng.missyou.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class WxAuthenticationService {
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private UserRepository userRepository;
    @Value("${wx.code2session}")
    private String code2SessionUrl;
    @Value("${wx.appid}")
    private String appid;
    @Value("${wx.appsecret}")
    private String appsecret;

    public String code2Session(String code) {
        String url = MessageFormat.format(this.code2SessionUrl, this.appid, this.appsecret, code);
        RestTemplate restTemplate = new RestTemplate();
        String sessionText = restTemplate.getForObject(url, String.class);
        Map<String, Object> session = new HashMap<>();
        try {
            session = mapper.readValue(sessionText, Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return this.registerUser(session);
    }

    private String registerUser(Map<String, Object> session) {
        String openid = (String) session.get("openid");
        if (openid == null) {
            throw new ParameterException(20004);
        }
        Optional<User> userOptional = this.userRepository.findByOpenid(openid);
        if (userOptional.isPresent()) {
            //TODO：返回JWT令牌
            return "";
        }
        User user = User.builder().openid(openid).build();
        this.userRepository.save(user);
        //TODO:返回JWT令牌
        return "";
    }

}
