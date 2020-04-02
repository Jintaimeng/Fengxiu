package com.meng.missyou.service;

import com.meng.missyou.model.User;
import com.meng.missyou.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserById(Long id) {
        return this.userRepository.findFirstById(id);
    }
}
