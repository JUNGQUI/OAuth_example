package com.example.jk.oauth.service;

import com.example.jk.oauth.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
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
}
