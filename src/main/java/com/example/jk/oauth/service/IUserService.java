package com.example.jk.oauth.service;

import javax.transaction.Transactional;

@Transactional
public interface IUserService {
    boolean checkPassword(String id, String password);
}
