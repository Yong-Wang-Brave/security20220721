package com.security.security20220721.service;

import com.security.security20220721.entity.UserInfo;
import com.security.security20220721.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    public UserInfo getUserInfo(String username){
        return userInfoMapper.getUserInfoByUsername(username);
    }

    public UserInfo getUserInfoByUsername(String username) {
        return userInfoMapper.getUserInfoByUsername(username);
    }
}