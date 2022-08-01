package com.security.security20220721.Common;

import com.security.security20220721.config.SpringContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.swing.*;

public class SecurityUtils {
    //获取当前登录的用户
    public static UserDetails getCurrentUser(){
        UserDetailsService userDetailsService = SpringContextHolder.getBean(UserDetailsService.class);
        return userDetailsService.loadUserByUsername(getCurrentUsername());
    }

    public static String getCurrentUsername() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication==null) {
            throw new BadRequestException(HttpStatus.UNAUTHORIZED,"当前登录状态过期");
        }
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails  userDetails=  (UserDetails)authentication.getPrincipal();
            return  userDetails.getUsername();
        }
        throw new BadRequestException(HttpStatus.UNAUTHORIZED,"找不到当前的登录的信息");


    }
}
