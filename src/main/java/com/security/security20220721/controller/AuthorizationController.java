package com.security.security20220721.controller;


import com.security.security20220721.Common.ApplicationContextUtils;
import com.security.security20220721.Common.Result;
import com.security.security20220721.Common.SecurityUtils;
import com.security.security20220721.annotations.rest.AnonymousGetMapping;
import com.security.security20220721.annotations.rest.AnonymousPostMapping;
import com.security.security20220721.config.StoreProperties;
import com.security.security20220721.entity.JwtUserDto;
import com.security.security20220721.entity.StoreProperties1;
import com.security.security20220721.entity.UserInfo;
import com.security.security20220721.utils.SecurityProperties;
import com.security.security20220721.config.TokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Api(tags= "系统:系统授权接口")
public class AuthorizationController {
    @Value("${store.logoutUrl}")
    private String logoutUrl;
    private final SecurityProperties properties;
    private final StoreProperties1 storeProperties1;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final StoreProperties storeProperties;
    @Autowired
    private AuthenticationManager authenticationManager;
    @ApiOperation("登录授权")
    @AnonymousPostMapping(value="/login")
    public Result login(HttpServletRequest request, HttpServletResponse httpServletResponse, @RequestBody UserInfo userInfo) throws Exception{

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

        UsernamePasswordAuthenticationToken authenticationToken =new UsernamePasswordAuthenticationToken(userInfo.getUsername(),
                userInfo.getPassword());

        //重点：此处会执行UserDetailServiceImpl
        request.setAttribute("pwd",userInfo.getPassword());
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

    @ApiOperation("获取用户信息")
    @GetMapping(value="/info")
    public Result getUserInfo(){
        return Result.sucess(SecurityUtils.getCurrentUser());
    }

@ApiOperation("退出登录")
    @AnonymousGetMapping(value="/caslogout")
    public Result logout(HttpServletRequest request,HttpServletResponse httpServletResponse)throws IOException {
String token =tokenProvider.getToken(request);
request.getSession().invalidate();
//方式一
String logoutUrl1 = storeProperties.getLogoutUrl();
//方式二： 见文件 ConfigBeanConfiguration
 String   logoutUrl2 = storeProperties1.getLogoutUrl();
 //方式三
    System.out.println(logoutUrl);
    //方式四  读取工程任意一个文件
    httpServletResponse.sendRedirect(logoutUrl);
return Result.sucess("退出成功");
}


}
