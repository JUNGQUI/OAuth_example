package com.example.jk.oauth.controller;

import com.example.jk.oauth.Util.ResourceUtil;
import com.example.jk.oauth.entity.JsonResult;
import com.example.jk.oauth.entity.User;
import com.example.jk.oauth.service.ISimpleDataService;
import com.example.jk.oauth.service.IUserService;
import com.example.jk.oauth.service.TokenResponseComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/simpleData")
public class SimpleDataController {

    private final ISimpleDataService simpleDataService;
    private final IUserService userService;
    private final TokenResponseComponent tokenResponseComponent;
    private final ResourceUtil resourceUtil;

    @Autowired
    public SimpleDataController(ISimpleDataService simpleDataService, IUserService userService, TokenResponseComponent tokenResponseComponent, ResourceUtil resourceUtil) {
        this.simpleDataService = simpleDataService;
        this.userService = userService;
        this.tokenResponseComponent = tokenResponseComponent;
        this.resourceUtil = resourceUtil;
    }


    @GetMapping(value = "/get")
    public JsonResult get(
            @RequestHeader(value = "Authorization") String authorization,
            HttpServletResponse response) {
        try {
            boolean result = tokenResponseComponent.checkToken(authorization);

            if (!result) {
                response.sendError(400, "invalid_token");
                return JsonResult.failure("invalid_token");
            }

            User user = userService.getByAccessToken(resourceUtil.extractToken(authorization));
            return JsonResult.success(resourceUtil.convert(simpleDataService.getByUser(user)));
        } catch (Exception ex) {
            return JsonResult.failure(ex.getMessage());
        }
    }
}
