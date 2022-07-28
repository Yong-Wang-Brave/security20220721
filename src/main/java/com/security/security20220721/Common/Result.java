package com.security.security20220721.Common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
public static  final  String SUCCESS_CODE ="0000";
public static final String SERVICE_ERROR_CODE="9999";
    private String  ret;
    private String msg;
    private T data;

    //优化升级
    public static <T> Result<T> sucess(){
        return  sucess("成功");
    }
    public static <T> Result<T> sucess(String  msg){
        return sucess(msg,null);
    }
    public static <T> Result<T> sucess(T data){
    return sucess("成功",data);
    }

    public static <T>Result<T> sucess(String msg,T data){

        return  new Result<>(SUCCESS_CODE,msg,data);
    }
public static <T> Result<T> sucess(String code,String msg,T data){
    return  new Result<>(code,msg,data); }

    public  static <T> Result<T>  failure(){
        return failure("失败");
    }

    public static <T> Result failure(String msg){
        return failure(SERVICE_ERROR_CODE,msg);
    }
    public static <T> Result<T> failure(String ret,String msg){
        return new Result<>(ret,msg,null);
    }
}
