package com.security.security20220721.config;


import com.security.security20220721.entity.LoginProperties;
import com.security.security20220721.utils.SecurityProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigBeanConfiguration {

   @Bean
   @ConfigurationProperties(prefix="login",ignoreUnknownFields = true)
    public LoginProperties loginProperties(){
        return  new LoginProperties();
    }

    @Bean
    @ConfigurationProperties(prefix="jwt",ignoreUnknownFields = true)
    public SecurityProperties securityProperties(){
        return  new SecurityProperties();
    }
}
