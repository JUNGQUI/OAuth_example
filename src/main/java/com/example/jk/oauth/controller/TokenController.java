package com.example.jk.oauth.controller;

import com.example.jk.oauth.entity.OAuthToken;
import com.example.jk.oauth.entity.User;
import com.example.jk.oauth.service.ITokenService;
import com.example.jk.oauth.service.IUserService;
import com.example.jk.oauth.Util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Controller
public class TokenController {

    @Value("${oauth.url}")
    private String oauthURL;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    private final ITokenService tokenService;
    private final TokenUtil tokenUtil;
    private final IUserService userService;

    @Autowired
    public TokenController(ITokenService tokenService, TokenUtil tokenUtil, IUserService userService) {
        this.tokenService = tokenService;
        this.tokenUtil = tokenUtil;
        this.userService = userService;
    }

    @GetMapping(value = "/authorize")
    public String authorize(@RequestParam(value = "client_id") String clientId,
                          @RequestParam(value = "state") String state,
                          @RequestParam(value = "redirect_uri") String redirectURI,
                          @RequestParam(value = "response_type") String responseType,
                          @RequestParam(value = "scope") String scope, Model model) {

        // 원래대로 라면 client-id, response-type 등으로 인증
        model.addAttribute("clientId", clientId);
        model.addAttribute("state", state);
        model.addAttribute("redirectURI", redirectURI);
        model.addAttribute("responseType", responseType);
        model.addAttribute("scope", scope);
        model.addAttribute("contextPath", contextPath);

        return "login";
    }

    @PostMapping(value = "/token")
    public void token(@RequestParam(value = "client_id") String clientId,
                               @RequestParam(value = "grant_type") String grantType,
                               @RequestParam(value = "code", required = false) String code,
                               @RequestParam(value = "refresh_token", required = false) String refreshToken,
                               HttpServletResponse response) throws IOException {
        try {
            OAuthToken token;

            switch (grantType) {
                case "authorization_code":
                    token = tokenService.getTokenByCode(code);
                    tokenUtil.responseAsJson(response, tokenUtil.convert(token));
                    break;
                case "refresh_token":
                    token = tokenService.getTokenByRefresh(refreshToken);
                    // 변경 전 user 가져오고 갱신
                    String beforeToken = token.getAccessToken();
                    token = tokenService.update(token);
                    userService.updateAccessToken(beforeToken, token.getAccessToken());
                    tokenUtil.responseAsJson(response, tokenUtil.convert(token));
                    break;
                default:
                    response.sendError(400, "invalid_grant_type");
                    break;
            }
        } catch (Exception ex) {
            switch (grantType) {
                case "authorization_code":
                    response.sendError(400, "invalid_code");
                    break;
                case "refresh_token":
                    response.sendError(400, "invalid_refresh_code");
                    break;
                default:
                    response.sendError(500, "OAuth server Internal Server Error.");
                    break;
            }
        }
    }

    @PostMapping(value="/code")
    public void code (@RequestParam(value = "clientId") String clientId,
                      @RequestParam(value = "state") String state,
                      @RequestParam(value = "redirectURI") String redirectURI,
                      @RequestParam(value = "scope") String scope,
                      @RequestParam(value = "loginId") String loginId,
                      HttpServletResponse response) throws IOException {
        try {
            String code = UUID.randomUUID().toString().replace("-", "");

            OAuthToken token = tokenService.save(code);
            User user = userService.get(loginId);

            user.setAccessToken(token.getAccessToken());

            response.sendRedirect(redirectURI + "?code=" + code + "&state=" + state);
        } catch (Exception ex) {
            response.sendError(400, "invalid code");
        }
    }
}
