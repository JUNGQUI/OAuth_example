package com.example.jk.oauth.controller;

import com.example.jk.oauth.entity.OAuthToken;
import com.example.jk.oauth.service.ITokenService;
import com.example.jk.oauth.service.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Controller
public class TokenController {

    @Value("${oauth.uri}")
    private String OAUTH_URI;

    @Value("${oauth.context}")
    private String CONTEXT_PATH;

    private final ITokenService tokenService;
    private final TokenUtil tokenUtil;

    @Autowired
    public TokenController(ITokenService tokenService, TokenUtil tokenUtil) {
        this.tokenService = tokenService;
        this.tokenUtil = tokenUtil;
    }

    @GetMapping(value = "/authorize")
    public String authorize(@RequestParam(value = "client_id") String clientId,
                          @RequestParam(value = "state") String state,
                          @RequestParam(value = "redirect_uri") String redirectURI,
                          @RequestParam(value = "response_type") String responseType,
                          @RequestParam(value = "scope") String scope, Model model) {

        model.addAttribute("clientId", clientId);
        model.addAttribute("state", state);
        model.addAttribute("redirectURI", redirectURI);
        model.addAttribute("responseType", responseType);
        model.addAttribute("scope", scope);

        return "login";
    }

    @PostMapping(value = "/token")
    public void token(@RequestParam(value="client_id") String clientId,
                               @RequestParam(value="grant_type") String grantType,
                               @RequestParam(value="code", required = false) String code,
                               @RequestParam(value="refresh_token", required = false) String refreshToken,
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
                    tokenUtil.responseAsJson(response, tokenUtil.convert(token));
                    break;
                default:
                    response.sendError(400, "Invalid grant type.");
                    break;
            }
        } catch (Exception ex) {
            switch (grantType) {
                case "authorization_code":
                    response.sendError(400, "Invalid code.");

                    break;
                case "refresh_token":
                    response.sendError(400, "Invalid refresh token.");
                    break;
                default:
                    response.sendError(503, "OAuth server Internal Server Error.");
                    break;
            }
        }
    }

    @PostMapping(value="/code")
    public void code (@RequestParam(value="clientId") String clientId,
                      @RequestParam(value="state") String state,
                      @RequestParam(value="redirectURI") String redirectURI,
                      @RequestParam(value="scope") String scope, HttpServletResponse response) throws IOException {
        try {
            String code = UUID.randomUUID().toString().replace("-", "");

            tokenService.save(code);

            response.sendRedirect(redirectURI + "?code=" + code + "&state=" + state);
        } catch (Exception ex) {
            response.sendError(400, "invalid code");
        }
    }
}
