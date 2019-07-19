package com.example.jk.oauth.service;

import com.example.jk.oauth.Util.ResourceUtil;
import com.example.jk.oauth.entity.OAuthToken;
import com.example.jk.oauth.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class TokenService implements ITokenService {

    private static final int expires_in = 30*24*60*60;

    private final TokenRepository tokenRepository;
    private final ResourceUtil resourceUtil;

    @Autowired
    public TokenService(TokenRepository tokenRepository, ResourceUtil resourceUtil) {
        this.tokenRepository = tokenRepository;
        this.resourceUtil = resourceUtil;
    }

    @Override
    public OAuthToken save(String code) {
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

        return token;
    }

    @Override
    public OAuthToken update(OAuthToken token) {
        String accessToken = UUID.randomUUID().toString();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, expires_in);

        token.setExpire(calendar.getTime());
        token.setExpiresIn(expires_in);
        token.setAccessToken(accessToken);

        return token;
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
