package com.example.jk.oauth.Util;

import com.example.jk.oauth.entity.SimpleData;
import com.example.jk.oauth.entity.SimpleDataDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ResourceUtil {

    public String extractToken (String authorization) {
        return authorization.replaceAll("Bearer ", "");
    }

    public SimpleDataDTO convert (SimpleData simpleData) {
        return new SimpleDataDTO(simpleData);
    }

    public List<SimpleDataDTO> convert (List<SimpleData> simpleData) {

        List<SimpleDataDTO> result = new ArrayList<>();

        for (SimpleData tempData : simpleData) {
            result.add(new SimpleDataDTO(tempData));
        }

        return result;
    }
}
