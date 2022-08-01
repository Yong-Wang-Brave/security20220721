package com.security.security20220721.config;

import com.alibaba.fastjson.JSON;
import com.security.security20220721.Common.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class ActivityCasFilter implements Filter {

    private String pacasLoginUrl;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext sc = filterConfig.getServletContext();
        WebApplicationContext cxt = WebApplicationContextUtils.getWebApplicationContext(sc);
        pacasLoginUrl = cxt.getEnvironment().getProperty("store.pacasLoginUrl");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("doFilter,Start..");
        //执行cas filter
        chain.doFilter(request,response);
        //响应跳转登录页
        HttpServletResponse resp=(HttpServletResponse)response;

        HttpServletRequest req= (HttpServletRequest)  request;

        log.info("doFilter,resp{}",resp);

        if (StringUtils.isNotBlank(req.getHeader("REQUIRES_CAS_INFO"))&&resp.getStatus()== HttpStatus.PERMANENT_REDIRECT.value()) {
            log.info("doFilter,header:{},status{}",req.getHeader("REQUIRES_CAS_INFO"),resp.getStatus());
            resp.setStatus(HttpStatus.OK.value());
            renderJson(resp, JSON.toJSONString(Result.failure("308","您为登录",pacasLoginUrl)));
        }


    }

    public  static void renderJson(HttpServletResponse response, String text) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        try( PrintWriter writer = response.getWriter()) {
           writer.print(text);
           writer.flush();;
        } catch (IOException e) {
         log.info("render json error",e);
        }
    }


}
