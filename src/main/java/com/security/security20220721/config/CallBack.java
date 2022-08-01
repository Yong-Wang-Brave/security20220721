package com.security.security20220721.config;

/**
 * 针对某些初始化方法，在springContextHolder 初始化前时
 * 可提交一个 提交回调任务
 * 在SpringContextHolder初始化后，进行回调使用
 */
public interface CallBack {

    /**
     * 回调执行方法
     */
    void executor();

//本回调任务名称
    default String getCallBackName(){
        return Thread.currentThread().getId()+":"+this.getClass().getName();
    }
}
