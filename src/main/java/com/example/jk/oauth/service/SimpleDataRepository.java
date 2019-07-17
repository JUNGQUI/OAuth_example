package com.example.jk.oauth.service;

import com.example.jk.oauth.entity.SimpleData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimpleDataRepository extends JpaRepository<SimpleData, String> {
    SimpleData findByUserId(String userId);
}
