package com.example.jk.oauth.service;

import com.example.jk.oauth.entity.SimpleData;
import com.example.jk.oauth.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimpleDataService implements ISimpleDataService {

    private final SimpleDataRepository simpleDataRepository;

    @Autowired
    public SimpleDataService(SimpleDataRepository simpleDataRepository) {
        this.simpleDataRepository = simpleDataRepository;
    }

    @Override
    public void save (User user, String message) {
        SimpleData simpleData = new SimpleData();
        simpleData.setMessage(message);
        simpleData.setUser(user);
        simpleDataRepository.save(simpleData);
    }

    @Override
    public void update (String id, String message) {
        SimpleData simpleData = simpleDataRepository.findByUserId(id);

        simpleData.setMessage(message);
        simpleDataRepository.save(simpleData);
    }

    @Override
    public SimpleData getByUser(User user) {
        return simpleDataRepository.findByUserId(user.getId());
    }
}
