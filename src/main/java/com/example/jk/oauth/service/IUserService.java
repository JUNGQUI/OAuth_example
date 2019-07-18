package com.example.jk.oauth.service;

import com.example.jk.oauth.entity.User;

import javax.transaction.Transactional;

@Transactional
public interface IUserService {
    boolean save(String id, String password);

    boolean checkPassword(String id, String password);

    User get(String id);

    User getByAccessToken(String accessToken);

    void updateAccessToken(String beforeToken, String afterToken);
}
