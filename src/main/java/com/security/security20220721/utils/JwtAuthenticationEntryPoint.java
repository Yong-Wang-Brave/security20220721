package com.security.security20220721.utils;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
     //当用户尝试访问安全的Rest资源而不提供任何凭据时，将调用此方法发送401响应
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,authException==null?"Unauthorized":authException.getMessage());
    }
}
