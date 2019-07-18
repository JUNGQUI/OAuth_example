package com.example.jk.oauth.service;

import com.example.jk.oauth.Util.ResourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenResponseComponent {

    private final TokenService tokenService;
    private final ResourceUtil resourceUtil;

    @Autowired
    public TokenResponseComponent(TokenService tokenService, ResourceUtil resourceUtil) {
        this.tokenService = tokenService;
        this.resourceUtil = resourceUtil;
    }

    public boolean checkToken(String authorization) {
        String accessToken = resourceUtil.extractToken(authorization);
        return tokenService.getTokenByAccess(accessToken) != null;
    }
}
