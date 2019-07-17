package com.example.jk.oauth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

@Configuration
public class OAuthConfig extends AuthorizationServerConfigurerAdapter {

    @Value("${oauth.url}")
    private String oauthURL;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.prefix(oauthURL + contextPath);
        endpoints.pathMapping("/oauth/token", contextPath + "/token")
                .pathMapping("/oauth/check_token", contextPath + "/check")
//                .pathMapping("/oauth/confirm_access", confirmPath)
//                .pathMapping("/oauth/token", tokenPath)
//                .pathMapping("/oauth/check_token", checkTokenPath)
//                .pathMapping("/oauth/token_key", tokenKeyPath)
//                .pathMapping("/oauth/authorize", authorizePath)
                ;
    }
}
