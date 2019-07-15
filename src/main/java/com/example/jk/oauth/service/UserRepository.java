package com.example.jk.oauth.service;

import com.example.jk.oauth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByLoginId(String loginId);
}
