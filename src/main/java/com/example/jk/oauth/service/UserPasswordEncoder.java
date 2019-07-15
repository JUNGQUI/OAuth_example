package com.example.jk.oauth.service;

import com.example.jk.oauth.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserPasswordEncoder implements IUserPasswordEncoder {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserPasswordEncoder(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public boolean checkPassword(String userName) {
        User user = userRepository.findByLoginId(userName);
        return passwordEncoder.matches("password", user.getPassword());
    }
}
