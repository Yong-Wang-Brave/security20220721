package com.security.security20220721.config.readConfig.readMyConfig.two;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

//手动加载自定义配置文件
@PropertySource(value = {
        "classpath:wangyong.properties",
}, encoding = "utf-8")
@Service
@Data
public class ServiceAImpl{

    @Value("${student.id}")
    private String id;

}