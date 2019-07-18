package com.example.jk.oauth.service;

import com.example.jk.oauth.entity.User;
import com.example.jk.oauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public boolean save(String id, String password) {
        User user = userRepository.findByLoginId(id);

        if (user != null) {
            return false;
        } else {
            user = new User();
            user.setLoginId(id);
            user.setPassword(password);
            userRepository.save(user);

            return true;
        }
    }

    @Override
    public boolean checkPassword(String id, String password) {
        User user = userRepository.findByLoginId(id);
        return passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public User get(String id) {
        return userRepository.findByLoginId(id);
    }

    @Override
    public User getByAccessToken(String accessToken) {
        return userRepository.findByAccessToken(accessToken);
    }

    @Override
    public void updateAccessToken(String beforeToken, String afterToken) {
        User user = userRepository.findByAccessToken(beforeToken);

        user.setAccessToken(afterToken);
        userRepository.save(user);
    }
}
