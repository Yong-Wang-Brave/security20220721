package com.security.security20220721.Common.Filter;

import com.security.security20220721.config.ActivityCasFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class FilterConfig {
    /**
     * 配置过滤器，这里过滤器主要是对返回值做后继处理
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean someFilterRegistration()
    {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new ActivityCasFilter());// 配置一个返回值加密过滤器
        registration.addUrlPatterns("/auth/info");
        registration.addUrlPatterns("/get-user");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("activityCasFilter");
        return registration;
    }
}
