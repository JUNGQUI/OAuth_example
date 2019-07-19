package com.example.jk.oauth.controller;

import com.example.jk.oauth.Util.ResourceUtil;
import com.example.jk.oauth.entity.JsonResult;
import com.example.jk.oauth.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/user")
public class UserController {

    private final IUserService userService;
    private final ResourceUtil resourceUtil;

    @Autowired
    public UserController(IUserService userService, ResourceUtil resourceUtil) {
        this.userService = userService;
        this.resourceUtil = resourceUtil;
    }

    @GetMapping(value="/save")
    public JsonResult save(@RequestParam(value="id") String id, @RequestParam(value="password") String password) {
        try {
            userService.saveOrUpdate(id, password, null);
            return JsonResult.success();
        } catch (Exception ex) {
            return JsonResult.failure(ex.getMessage());
        }
    }

    @GetMapping(value = "/getByAccessToken")
    public JsonResult getByAccessToken(@RequestHeader(value = "Authorization") String authorization) {
        try {
            return JsonResult.success(userService.getByAccessToken(resourceUtil.extractToken(authorization)));
        } catch (Exception ex) {
            return JsonResult.failure(ex.getMessage());
        }
    }


}
