package com.security.security20220721.controller;

import com.security.security20220721.entity.UserInfo;
import com.security.security20220721.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class HelloController {
    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/get-user")
    public UserInfo getUser(@RequestParam String username){
        return userInfoService.getUserInfo(username);
    }

    @PreAuthorize("hasRole('USER')") // 只能user角色才能访问该方法
    @GetMapping("/user")
    public String user(){
        return "user角色访问";
    }

    @PreAuthorize("hasRole('ADMIN')") // 只能admin角色才能访问该方法
    @GetMapping("/admin")
    public String admin(){
        return "admin角色访问";
    }

    @PostMapping("/add-user")
    public int addUser(@RequestBody UserInfo userInfo){
        return userInfoService.insertUser(userInfo);
    }
    //可以用谷歌浏览器修改秘密
    @PutMapping("/updatePwd")
    public int updatePwd(@RequestBody Map<String, String> map){
        return userInfoService.updatePwd(map.get("oldPwd"), map.get("newPwd"));
    }

}
