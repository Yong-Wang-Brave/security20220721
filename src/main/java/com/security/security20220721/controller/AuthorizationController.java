package com.security.security20220721.controller;


import com.security.security20220721.Common.ApplicationContextUtils;
import com.security.security20220721.Common.Result;
import com.security.security20220721.annotations.rest.AnonymousGetMapping;
import com.security.security20220721.entity.JwtUserDto;
import com.security.security20220721.utils.SecurityProperties;
import com.security.security20220721.config.TokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Api(tags= "系统:系统授权接口")
public class AuthorizationController {
    private final SecurityProperties properties;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @ApiOperation("登录授权")
    @AnonymousGetMapping(value="/login")
    public Result login(HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception{

        String activeProfile = ApplicationContextUtils.getActiveProfile();
        String loginUserName;
        if ("dev".equals(activeProfile)) {
            loginUserName="wy";
        }else{
            //密码解密
            Object attribute = request.getSession().getAttribute("loginUserName");
            if (null==attribute) {
                httpServletResponse.sendRedirect("storeProperties.getLoginUrl()");
                return Result.sucess(null);
            }
            loginUserName=attribute.toString();
        }

        UsernamePasswordAuthenticationToken authenticationToken =new UsernamePasswordAuthenticationToken("11","11");

        //重点：此处会执行UserDetailServiceImpl
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        //生成令牌
        String token = tokenProvider.createToken(authenticate);
      final JwtUserDto jwtUserDto=  (JwtUserDto)authenticate.getPrincipal();
        HashMap<String, Object> authInfo = new HashMap<String, Object>(2) {
            {
                put("token", properties.getTokenStartWith() + token);
                put("user", jwtUserDto);
            }
        };
        return Result.sucess(authInfo);

    }




}
