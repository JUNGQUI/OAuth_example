package com.example.jk.oauth.controller;

import com.example.jk.oauth.entity.JsonResult;
import com.example.jk.oauth.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value="/login")
public class LoginController {

    private final IUserService userService;

    @Autowired
    public LoginController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/check")
    public JsonResult loginCheck (@RequestParam(value = "id") String id,
                                  @RequestParam(value = "password") String password) {
        try {
            boolean result = userService.checkPassword(id, password);

            if (result) {
                return JsonResult.success();
            } else {
                return JsonResult.failure("인증 실패");
            }

        } catch (Exception ex) {
            return JsonResult.failure(ex.getMessage());
        }
    }
}
