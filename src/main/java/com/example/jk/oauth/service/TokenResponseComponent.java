package com.example.jk.oauth.service;

import com.example.jk.oauth.Util.ResourceUtil;
import com.example.jk.oauth.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenResponseComponent {

    private final ITokenService tokenService;
    private final IUserService userService;
    private final ResourceUtil resourceUtil;

    @Autowired
    public TokenResponseComponent(ITokenService tokenService, IUserService userService, ResourceUtil resourceUtil) {
        this.tokenService = tokenService;
        this.userService = userService;
        this.resourceUtil = resourceUtil;
    }

    public boolean checkToken(String authorization) {
        String accessToken = resourceUtil.extractToken(authorization);
        return tokenService.getTokenByAccess(accessToken) != null;
    }

    public User getUserByToken (String authorization) {
        String accessToken = resourceUtil.extractToken(authorization);
        return userService.getByAccessToken(accessToken);
    }
}
