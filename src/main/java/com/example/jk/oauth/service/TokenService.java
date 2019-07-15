package com.example.jk.oauth.service;

import com.example.jk.oauth.entity.OAuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Service
public class TokenService implements ITokenService {

    private final static Random random = new Random();
    private static final int expires_in = 30*24*60*60;

    private final TokenRepository tokenRepository;

    @Autowired
    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void save(String code) {
        OAuthToken token = new OAuthToken();

        String accessToken = UUID.randomUUID().toString();
        String refreshToken = UUID.randomUUID().toString();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, expires_in);

        token.setCode(code);
        token.setCreatedDate(new Date());
        token.setExpire(calendar.getTime());
        token.setExpiresIn(expires_in);
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);

        tokenRepository.save(token);
    }

    @Override
    public void update(OAuthToken token) {
        String accessToken = UUID.randomUUID().toString();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, expires_in);

        token.setExpire(calendar.getTime());
        token.setExpiresIn(expires_in);
        token.setAccessToken(accessToken);
    }

    @Override
    public OAuthToken getTokenByCode(String code) {
        return tokenRepository.findByCode(code);
    }

    @Override
    public OAuthToken getTokenByAccess(String accessToken) {
        return tokenRepository.findByAccessTokenAndExpireAfter(accessToken, new Date());
    }

    @Override
    public OAuthToken getTokenByRefresh(String refreshToken){
        return tokenRepository.findByRefreshToken(refreshToken);
    }
}