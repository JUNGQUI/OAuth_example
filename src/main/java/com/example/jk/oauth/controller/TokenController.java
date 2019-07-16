package com.example.jk.oauth.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TokenController {

    @Value("${oauth.uri}")
    private String OAUTH_URI;

    @Value("${oauth.context}")
    private String CONTEXT_PATH;

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
}
