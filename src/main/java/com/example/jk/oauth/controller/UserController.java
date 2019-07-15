package com.example.jk.oauth.controller;

import com.example.jk.oauth.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @Autowired

    @GetMapping(value = "/checkPassword")
    public boolean checkPassword () {
        try {
            return userService.checkPassword("jklee");
        } catch (Exception ex) {
            return false;
        }
    }
}
