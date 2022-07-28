package com.security.security20220721.utils;

import lombok.Data;

@Data
public class SecurityProperties {
    //Authorization
    private String header;
    //令牌前缀：最后留个空格 Bearer
    private String tokenStartWith;
    //必须使用最少88位的base64对该令牌进行编码
    private String base64Secret;
    //令牌过期时间，此处单位毫秒
    private Long tokenValidityInSeconds;
    //在线用户key,根据key查询redis中在线用户的数据
    private String onlineKey;
    //验证码 key
    private String codeKey;
    //token续期检查
    private Long detect;
    //续期时间
    private Long renew;
    public String getTokenStartWith(){
        return tokenStartWith+" ";
    }
}
