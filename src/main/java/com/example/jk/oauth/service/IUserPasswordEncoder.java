package com.example.jk.oauth.service;

import javax.transaction.Transactional;

@Transactional
public interface IUserPasswordEncoder {
    boolean checkPassword(String userName);
}
