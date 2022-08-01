package com.security.security20220721.config;


import com.security.security20220721.entity.LoginProperties;
import com.security.security20220721.entity.StoreProperties1;
import com.security.security20220721.utils.SecurityProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

//把配置文件中的属性加载到bean里面
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

    @Bean
    @ConfigurationProperties(prefix="store1",ignoreUnknownFields = true)
    public StoreProperties1 storeProperties1(){
        return  new StoreProperties1();
    }
}
