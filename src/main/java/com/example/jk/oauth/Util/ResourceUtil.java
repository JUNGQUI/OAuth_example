package com.example.jk.oauth.Util;

import com.example.jk.oauth.entity.SimpleData;
import com.example.jk.oauth.entity.SimpleDataDTO;
import org.springframework.stereotype.Component;

@Component
public class ResourceUtil {

    public String extractToken (String authorization) {
        return authorization.replaceAll("Bearer ", "");
    }

    public SimpleDataDTO convert (SimpleData simpleData) {
        return new SimpleDataDTO(simpleData);
    }
}
