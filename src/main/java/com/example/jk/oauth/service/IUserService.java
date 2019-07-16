package com.example.jk.oauth.service;

import javax.transaction.Transactional;

@Transactional
public interface IUserService {
    boolean save(String id, String password);

    boolean checkPassword(String id, String password);
}
