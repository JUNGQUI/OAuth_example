package com.example.jk.oauth.controller;

import com.example.jk.oauth.service.IUserPasswordEncoder;
import com.example.jk.oauth.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserRepository userRepository;
    private final IUserPasswordEncoder userPasswordEncoder;

    @Autowired
    public UserController(UserRepository userRepository, IUserPasswordEncoder userPasswordEncoder) {
        this.userRepository = userRepository;
        this.userPasswordEncoder = userPasswordEncoder;
    }

    @GetMapping(value = "/checkPassword")
    public boolean checkPassword () {
        try {
            return userPasswordEncoder.checkPassword("jklee");
        } catch (Exception ex) {
            return false;
        }
    }
}
