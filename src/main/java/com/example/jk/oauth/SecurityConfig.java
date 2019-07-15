package com.example.jk.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/login", "/login/check","/authorization-code").permitAll();

        http.csrf().disable();
        http.authorizeRequests().antMatchers("/oauth/**").permitAll();
    }

    public void configure(WebSecurity web) {
        // @EnableWebSecurity 를 사용하면 WebSecurity 를 이용해서 권한을 풀어줘야 함
        web.ignoring().antMatchers("/resources/**").antMatchers("/webjars/**");
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder) throws Exception {
        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder)
                .withUser("jklee")
                .password(passwordEncoder.encode("password"))
                .roles("USER");
    }
}