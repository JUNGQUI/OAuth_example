package com.example.jk.oauth.controller;

import com.example.jk.oauth.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class LoginController {

    private final IUserService userService;

    @Autowired
    public LoginController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/login/check")
    public void loginCheck (HttpServletResponse response,
                            @RequestParam(value = "id") String id,
                            @RequestParam(value = "password") String password) {
        try {
            boolean result = userService.checkPassword(id, password);

            if (result) {

            } else {

            }

        } catch (Exception ex) {

        }
    }
}
