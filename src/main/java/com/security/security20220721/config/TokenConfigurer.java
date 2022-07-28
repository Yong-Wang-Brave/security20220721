package com.security.security20220721.config;

import com.security.security20220721.utils.SecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class TokenConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final TokenProvider tokenProvider;
    private  final SecurityProperties properties;


    @Override
  public  void configure(HttpSecurity http) {
        TokenFilter customFilter =new TokenFilter(tokenProvider, properties);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
