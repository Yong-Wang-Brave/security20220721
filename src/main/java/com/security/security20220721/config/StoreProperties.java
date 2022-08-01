package com.security.security20220721.config;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@Component
@ConfigurationProperties(prefix = "store")
public class StoreProperties {

    private String loginUrl;
    private String logoutUrl;
    private String pacasLoginUrl;
}
