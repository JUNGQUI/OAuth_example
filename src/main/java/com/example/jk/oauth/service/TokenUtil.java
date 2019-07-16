package com.example.jk.oauth.service;

import com.example.jk.oauth.entity.OAuthToken;
import com.example.jk.oauth.entity.OAuthTokenDTO;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenUtil {

    public OAuthTokenDTO convert(OAuthToken oAuthToken) {
        OAuthTokenDTO oAuthTokenDTO = new OAuthTokenDTO();

        oAuthTokenDTO.setAccess_token(oAuthToken.getAccessToken());
        oAuthTokenDTO.setRefresh_token(oAuthToken.getAccessToken());
        oAuthTokenDTO.setCode(oAuthToken.getCode());
        oAuthTokenDTO.setToken_type("Bearer");
        oAuthTokenDTO.setExpires_in(oAuthToken.getExpiresIn());
        oAuthTokenDTO.setExpire(oAuthToken.getExpire());

        return oAuthTokenDTO;
    }

    public OAuthTokenDTO convertError(String error, String errorMessage) {
        OAuthTokenDTO oAuthTokenDTO = new OAuthTokenDTO();
        oAuthTokenDTO.setError(error);
        oAuthTokenDTO.setError_description(errorMessage);

        return oAuthTokenDTO;
    }

    public void responseAsJson (HttpServletResponse response, Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String tokenJsonString = mapper.writeValueAsString(object);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(tokenJsonString);
        response.getWriter().flush();
        response.getWriter().close();
    }
}
