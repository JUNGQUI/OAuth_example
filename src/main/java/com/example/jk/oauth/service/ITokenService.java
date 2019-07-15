package com.example.jk.oauth.service;

import com.example.jk.oauth.entity.OAuthToken;

import javax.transaction.Transactional;

@Transactional
public interface ITokenService {
    void save(String code);

    void update(OAuthToken token);

    OAuthToken getTokenByCode(String code);

    OAuthToken getTokenByAccess(String accessToken);

    OAuthToken getTokenByRefresh(String refreshToken);
}
