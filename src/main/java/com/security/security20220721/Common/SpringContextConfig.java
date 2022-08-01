package com.security.security20220721.Common;


import com.security.security20220721.config.SpringContextHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringContextConfig {
    @Bean
    public SpringContextHolder springContextHolder(){
        return new SpringContextHolder();
    }
}
