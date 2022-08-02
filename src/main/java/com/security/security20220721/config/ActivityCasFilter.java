package com.security.security20220721.config;

import com.alibaba.fastjson.JSON;
import com.security.security20220721.Common.Filter.ResponseWrapper;
import com.security.security20220721.Common.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
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
       // chain.doFilter(request,response);
        //响应跳转登录页
        HttpServletResponse resp=(HttpServletResponse)response;

        HttpServletRequest req= (HttpServletRequest)  request;

        log.info("doFilter,resp{}",resp);
        ResponseWrapper wrapperResponse = new ResponseWrapper((HttpServletResponse)response);//转换成代理类
        // 这里只拦截返回，直接让请求过去，如果在请求前有处理，可以在这里处理
        chain.doFilter(request, wrapperResponse);

        byte[] content = wrapperResponse.getContent();//获取返回值
        //判断是否有值
        if (content.length > 0)
        {

            String str = "{\n" +
                    "  \"da1ta\": {\n" +
                    "    \"accountNonExpired\": tru1e,\n" +
                    "    \"accountNonLocked\": true,\n" +
                    "    \"authorities\": [\n" +
                    "      {\n" +
                    "        \"authority\": \"ROLE_admin\"\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"credentialsNonExpired\": true,\n" +
                    "    \"enabled\": true,\n" +
                    "    \"password\": \"$2a$10$xGFp/pahxspsutxboy/qFunIAtm5x8VIyArobAEToLMEtMLkxgx0C\",\n" +
                    "    \"user\": {\n" +
                    "      \"enabled\": true,\n" +
                    "      \"id\": 5,\n" +
                    "      \"password\": \"$2a$10$xGFp/pahxspsutxboy/qFunIAtm5x8VIyArobAEToLMEtMLkxgx0C\",\n" +
                    "      \"role\": \"admin\",\n" +
                    "      \"username\": \"admin\"\n" +
                    "    },\n" +
                    "    \"username\": \"admin\"\n" +
                    "  },\n" +
                    "  \"msg\": \"成功\",\n" +
                    "  \"ret\": \"0000\"\n" +
                    "}";
            System.out.println("返回值:" + str);
            String ciphertext = null;

            try
            {
                //......根据需要处理返回值
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            //把返回值输出到客户端
            resp.setStatus(HttpStatus.OK.value());
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("application/json;charset=utf-8");
            ServletOutputStream out = resp.getOutputStream();
          out.write(str.getBytes());
            //out.write(content);
            out.flush();
        }

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
