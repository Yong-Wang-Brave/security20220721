package com.security.security20220721.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
public class UserInfo {
    private int id;
    private String username;
    private String password;
    private String role;
    private Boolean enabled;
    private String oldPwd;
    private String newPwd;

}
