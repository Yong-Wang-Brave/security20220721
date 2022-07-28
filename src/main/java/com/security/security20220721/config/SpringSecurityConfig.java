package com.security.security20220721.config;

import com.security.security20220721.Common.UserDetailsServiceImpl;
import com.security.security20220721.RequestMethodEnum;
import com.security.security20220721.annotations.AnonymousAccess;
import com.security.security20220721.utils.JwtAccessDeniedHandler;
import com.security.security20220721.utils.JwtAuthenticationEntryPoint;
import com.security.security20220721.utils.SecurityProperties;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
     private final TokenProvider tokenProvider;
     private  final CorsFilter corsFilter;
     private final JwtAuthenticationEntryPoint authenticationErrorHandler;
     private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final ApplicationContext applicationContext;
    private final SecurityProperties properties;
@Autowired
UserDetailsServiceImpl userDetailsService;

    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
     GrantedAuthorityDefaults grantedAuthorityDefaults(){
         //去除role_前缀
         return new GrantedAuthorityDefaults("");
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
         //密码加密方式
         return  new BCryptPasswordEncoder();
    }

     @Override
     protected void configure(HttpSecurity httpSecurity) throws Exception {
          //搜索匿名标记url @AnonymousAccess
          RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping)applicationContext.getBean("requestMappingHandlerMapping");
         Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = requestMappingHandlerMapping.getHandlerMethods();
         Map<String, Set<String>> annoymousUrl = getAnnoymousUrl(handlerMethodMap);
         httpSecurity
                 //禁用CRSF
                 .csrf().disable()
                 .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                 //授权异常
                 .exceptionHandling()
                 .authenticationEntryPoint(authenticationErrorHandler)
                 .accessDeniedHandler(jwtAccessDeniedHandler)
                 //防止iframe造成跨域
                 .and()
                 .headers()
                 .frameOptions()
                 .disable()
                 //不创建会话
                 .and()
                 .sessionManagement()
                 .sessionCreationPolicy(SessionCreationPolicy.NEVER)
                 .and()
                 .authorizeRequests()
                 .antMatchers(
                         HttpMethod.GET,
                         "/*.html",
                         "/**/*.html",
                         "/**/*.js",
                         "webSocket/**"
                 ).permitAll()
                 //swagger文档
                 .antMatchers("/swagger-ui.html").permitAll()
                 .antMatchers("/swagger-resources/**").permitAll()
                 .antMatchers("/CAS_SUCESS_LOGIN").permitAll()
                 .antMatchers("/outer/**").permitAll()
                 .antMatchers("/appsvr/health/**").permitAll()
                 .antMatchers("/caslogout").permitAll()
                 .antMatchers("/webjars/**").permitAll()
                 .antMatchers("/*/api-docs").permitAll()
                 //文件
                 .antMatchers("/avatar/**").permitAll()
                 .antMatchers("/file/**").permitAll()
                 //阿里巴巴druid
                 .antMatchers("/druid/**").permitAll()
                 //放行options请求
                 .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                 //自定义匿名访问所有的url放行，允许匿名带tokende 访问，细腻话到每个request类型
                 .antMatchers(HttpMethod.GET,annoymousUrl.get(RequestMethodEnum.GET.getType()).toArray(new String[0])).permitAll()
                 .antMatchers(HttpMethod.POST,annoymousUrl.get(RequestMethodEnum.POST.getType()).toArray(new String[0])).permitAll()
                 .antMatchers(HttpMethod.PUT,annoymousUrl.get(RequestMethodEnum.PUT.getType()).toArray(new String[0])).permitAll()
                 .antMatchers(HttpMethod.PATCH,annoymousUrl.get(RequestMethodEnum.PATCH.getType()).toArray(new String[0])).permitAll()
                 .antMatchers(HttpMethod.DELETE,annoymousUrl.get(RequestMethodEnum.DELETE.getType()).toArray(new String[0])).permitAll()
                 //所有类型的接口都放行
                 .antMatchers(annoymousUrl.get(RequestMethodEnum.ALL.getType()).toArray(new String[0])).permitAll()
                 //所有的请求都需要认证
                 .anyRequest().authenticated()
                 .and().apply(securityConfigurerAdapter());

     }

     private Map<String, Set<String>>  getAnnoymousUrl(Map<RequestMappingInfo,HandlerMethod> handlerMethodMap){

         Map<String, Set<String>> anonymousUrls= new HashMap<>(6);

         Set<String> get =new HashSet<>();
         Set<String> post =new HashSet<>();
         Set<String> put =new HashSet<>();
         Set<String> patch =new HashSet<>();
         Set<String> delete =new HashSet<>();
         Set<String> all =new HashSet<>();

         for (Map.Entry<RequestMappingInfo, HandlerMethod> infoEntry : handlerMethodMap.entrySet()) {
             HandlerMethod handlerMethod = infoEntry.getValue();
             AnonymousAccess anonymousAccess = handlerMethod.getMethodAnnotation(AnonymousAccess.class);

             if (anonymousAccess!=null) {
                 List<RequestMethod> requestMethods = new ArrayList<>(infoEntry.getKey().getMethodsCondition().getMethods());
                 RequestMethodEnum request=  RequestMethodEnum.find(requestMethods.size()==0?RequestMethodEnum.ALL.getType() : requestMethods.get(0).name());
                 switch(Objects.requireNonNull(request)){
                     case GET:
                         get.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                         break;
                     case POST:
                         post.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                         break;
                     case PUT:
                         put.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                         break;
                     case PATCH:
                         patch.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                         break;
                     case DELETE:
                         delete.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                         break;
                     default:
                         all.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                         break;

                 }

             }

         }

         anonymousUrls.put(RequestMethodEnum.GET.getType(),get);
         anonymousUrls.put(RequestMethodEnum.POST.getType(),post);
         anonymousUrls.put(RequestMethodEnum.PUT.getType(),put);
         anonymousUrls.put(RequestMethodEnum.PATCH.getType(),patch);
         anonymousUrls.put(RequestMethodEnum.DELETE.getType(),delete);
         anonymousUrls.put(RequestMethodEnum.ALL.getType(),all);

         return anonymousUrls;




}


private TokenConfigurer securityConfigurerAdapter(){

        return new TokenConfigurer(tokenProvider,properties);
    }

}
