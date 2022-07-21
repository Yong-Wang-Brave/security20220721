package com.security.security20220721.controller;

import com.security.security20220721.entity.UserInfo;
import com.security.security20220721.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    private UserInfoService userInfoService;
    
    @GetMapping("/get-user")
    public UserInfo getUser(@RequestParam String username){
        return userInfoService.getUserInfoByUsername(username);
    }
}
