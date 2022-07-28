package com.security.security20220721.config;

import com.security.security20220721.utils.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenFilter extends GenericFilterBean {

    private static final Logger log= LoggerFactory.getLogger(TokenFilter.class);
    private final TokenProvider tokenProvider;

    private final SecurityProperties properties;

    public TokenFilter(TokenProvider tokenProvider, SecurityProperties properties) {
        this.tokenProvider = tokenProvider;
        this.properties = properties;
    }




    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest=  (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse=  (HttpServletResponse) servletResponse;
       String token= resolveToken(httpServletRequest);
       //对于Token为空的不需要去查redis
        if (!StringUtils.isEmpty(token)) {
            if (StringUtils.hasText(token)) {
                Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
filterChain.doFilter(servletRequest,servletResponse);
    }

    private String resolveToken(HttpServletRequest request){
        String bearToken = request.getHeader(properties.getHeader());
        if(StringUtils.hasText(bearToken)&&bearToken.startsWith(properties.getTokenStartWith())){
            //去掉令牌前缀
            return bearToken.replace(properties.getTokenStartWith(),"");

        }else{
            log.debug("非法Token:{}",bearToken);
        }
        return null;
    }



}
