package com.security.security20220721.utils;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class  JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        //当用户在没有授权的情况下访问受保护的rest的资源时，将调用此方法发送403 Forbidden响应。
        response.sendError(HttpServletResponse.SC_FORBIDDEN,e.getMessage());
    }
}
