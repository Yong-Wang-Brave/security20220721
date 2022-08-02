package com.security.security20220721.Common.Filter;


import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 返回值输出过滤器，这里用来加密返回值
 *
 * @Title: ResponseFilter
 * @Description:
 * @author kokJuis
 * @date 上午9:52:42
 */

public class ResponseFilter implements Filter
{

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException
    {
        ResponseWrapper wrapperResponse = new ResponseWrapper((HttpServletResponse)response);//转换成代理类
        // 这里只拦截返回，直接让请求过去，如果在请求前有处理，可以在这里处理
        filterChain.doFilter(request, wrapperResponse);
        byte[] content = wrapperResponse.getContent();//获取返回值
        //判断是否有值
        if (content.length > 0)
        {

            String str = new String(content, "UTF-8");
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
            ServletOutputStream out = response.getOutputStream();
            out.write(str.getBytes());
            out.flush();
            return;
        }

    }

    public void init(FilterConfig arg0)
            throws ServletException
    {

    }

    @Override
    public void destroy()
    {

    }

}