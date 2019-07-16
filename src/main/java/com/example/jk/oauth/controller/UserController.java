package com.example.jk.oauth.controller;

import com.example.jk.oauth.entity.JsonResult;
import com.example.jk.oauth.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/user")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping(value="/save")
    public JsonResult save(@RequestParam(value="id") String id, @RequestParam(value="password") String password) {
        try {
            if (userService.save(id, password)) {
                return JsonResult.success();
            } else {
                return JsonResult.failure("계정 생성 실패");
            }
        } catch (Exception ex) {
            return JsonResult.failure(ex.getMessage());
        }
    }

}
