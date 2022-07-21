package com.security.security20220721.entity;

import lombok.Data;

@Data
public class UserInfo {
    private int id;
    private String username;
    private String password;
    private String role;
}
