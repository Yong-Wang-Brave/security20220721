package com.security.security20220721.entity;

import lombok.Data;

@Data
public class LoginProperties {
    private boolean singleLogin =false;
    //用户登录信息缓存
    private boolean cacheEnable;

    public boolean isSingleLogin() {
        return singleLogin;
    }

    public void setSingleLogin(boolean singleLogin) {
        this.singleLogin = singleLogin;
    }

    public boolean isCacheEnable() {
        return cacheEnable;
    }

    public void setCacheEnable(boolean cacheEnable) {
        this.cacheEnable = cacheEnable;
    }
}
