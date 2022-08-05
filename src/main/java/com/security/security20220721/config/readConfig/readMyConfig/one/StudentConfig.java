package com.security.security20220721.config.readConfig.readMyConfig.one;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component // 交给Spring容器管理
@PropertySource("classpath:wangyong.properties") // 加载自定义配置文件
@ConfigurationProperties(prefix="student") // 配置属性，设置前缀
@Data
public class StudentConfig {
    private String id;
    private String name;
    private int age;
}
/*
————————————————
版权声明：本文为CSDN博主「@csl」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/m0_46204302/article/details/118124182*/
