package com.example.jk.oauth.service;

import com.example.jk.oauth.entity.SimpleData;
import com.example.jk.oauth.entity.User;

import javax.transaction.Transactional;

@Transactional
public interface ISimpleDataService {
    void save(User user, String message);

    void update(String id, String message);

    SimpleData getByUser(User user);
}
