package com.security.security20220721.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    //没被包装过得HttpServletRequest (特殊场景需要自己过滤)
    HttpServletRequest orgRequest;
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        orgRequest=request;
    }
}
