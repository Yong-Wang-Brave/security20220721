package com.security.security20220721.Common;

import org.apache.ibatis.reflection.ArrayUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ApplicationContextUtils implements ApplicationContextAware {
private static  ApplicationContext applicationContext;
    //获取applicationContext
    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtils.applicationContext=applicationContext;
    }

    //通过name获取bean

    public static Object getBean(String name){

        return  getApplicationContext().getBean(name);
    }

    public static <T> T getBean(Class<T> name){

        return  (T) getApplicationContext().getBean(name);
    }

    //获取spring.profiles.active
    public static String getActiveProfile(){
        String[] activeProfiles = getApplicationContext().getEnvironment().getActiveProfiles();
        if (Objects.isNull(activeProfiles)||activeProfiles.length==0) {
            return "dev";
        }
        return  getApplicationContext().getEnvironment().getActiveProfiles()[0];
    }

}
