package com.security.security20220721.config;

import jdk.nashorn.internal.codegen.CompilerConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {
    private static ApplicationContext applicationContext=null;
    private static final List<CallBack> CALL_BACKS=new ArrayList<>();
private static  boolean addCallBack= true;

//针对某些初始化方法，在springContextHolder 未初始化时，提交回调方法
    //在SpringContextHolder初始化后进行回调使用
    public synchronized  static void  addCallBacks(CallBack callBack){
        if(addCallBack){
            SpringContextHolder.CALL_BACKS.add(callBack);
        }else{
            log.warn("CallBack:{}已无法添加！立即执行",callBack.getCallBackName());
            callBack.executor();
        }
    }

    //从静态变量applicationContext中取得bean,自动转型为所赋值对象的类型
    public static<T> T getBean(String name){
        assertContextInjected();
        return (T) applicationContext.getBean(name);
    }

    //从静态变量applicationContext中取得bean,自动转型为所赋值对象的类型
    public static<T> T getBean(Class<T> requiredType){
        assertContextInjected();
        return applicationContext.getBean(requiredType);
    }
//获取springBoot的配置信息
    public static <T> T getProperties(String property, T defaultValue, Class<T> requiredType){
        T result=defaultValue;

        try {
            return getBean(Environment.class).getProperty(property,requiredType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  result;
    }

//获取SpringBoot配置信息
    public static String getProperties(String property){
        return  getProperties(property,null,String.class);
    }

    //获取springboot配置信息
    public static<T> T getProperties(String property,Class<T> requiredType){
        return getProperties(property,null,requiredType);
    }




    //检查ApplicationContext 不为空
    private static void assertContextInjected(){
        if (applicationContext==null) {
            throw new IllegalStateException("applicationContext属性未注入，请在applicationContext"+".xml中定义SpringContextHolder" +
                    "或在SpringBoot启动类中注册SpringContextHolder");
        }
    }


    //清除SpringContextHolder中的Applicationtext为null

    public static void clearHolder(){{
    log.debug("清除SpringContextHolder中的ApplicationContexr"+applicationContext);
    applicationContext=null;
    }
    }


    @Override
    public void destroy()  {
SpringContextHolder.clearHolder();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringContextHolder.applicationContext!=null) {
            log.warn("SpringContextHolder中的ApplicationContext被覆盖，原有的ApplicationContext为："+SpringContextHolder.applicationContext);
        }
        SpringContextHolder.applicationContext=applicationContext;
        if (addCallBack) {
            for (CallBack callBack : SpringContextHolder.CALL_BACKS) {
                callBack.executor();
            }
            CALL_BACKS.clear();
        }
        SpringContextHolder.addCallBack=false;
    }
}
