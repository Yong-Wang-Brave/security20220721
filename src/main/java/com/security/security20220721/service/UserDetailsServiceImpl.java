package com.security.security20220721.service;

import com.security.security20220721.Common.ContextHolderUtils;
import com.security.security20220721.entity.JwtUserDto;
import com.security.security20220721.entity.LoginProperties;
import com.security.security20220721.entity.UserInfo;
import com.security.security20220721.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@RequiredArgsConstructor
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final LoginProperties loginProperties;

    public void setEnableCache(boolean enableCache) {
        this.loginProperties.setCacheEnable(enableCache);
    }

    @Autowired
    UserInfoService userInfoService;
    //用户信息缓存
    static Map<String, JwtUserDto> userDtoCache = new ConcurrentHashMap();

    @Override
    public UserDetails loadUserByUsername(String username) {
        JwtUserDto jwtUserDto = null;
        UserInfo user;
        // 角色集合
        List<GrantedAuthority> authorities = new ArrayList<>();


           /* user.setPassword("$2a$10$ZAlZDVATJs7NaI5ALGF.seP5LsOIzGTVpkxrWtYRSP8d0WumzJ2Q6");
            user.setUsername("RUN576882");*/
       /*        user.setPassword("$2a$10$YgwkqFAva7i2TMwrd6v4BOiT5hcL4ab3nEVvkBHOd64oItGOhp9t.");
            user.setUsername("111");*/
            user = userInfoService.getUserInfo(username);
            if (user == null) {
                throw new UsernameNotFoundException("用户不存在");
            }
            HttpServletRequest pwd = ContextHolderUtils.getRequest();
            Object pwd1 = pwd.getAttribute("pwd");
            // 判断输入的旧密码是正确
            if (pwd1!=null&&!passwordEncoder.matches(String.valueOf(pwd1),user.getPassword())) {
                // 不要忘记加密新密码
                throw  new RuntimeException("密码错误");
            }
            user.setEnabled(Boolean.TRUE);

            // 得到用户角色
            String role = user.getRole();


            // 角色必须以`ROLE_`开头，数据库中没有，则在这里加
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));


        if (user == null) {
            throw new UsernameNotFoundException("");
        } else {
            if (!user.getEnabled()) {
                throw new RuntimeException("账号未激活");
            }
            //

            jwtUserDto = new JwtUserDto(user,authorities);
            Collection<? extends GrantedAuthority> authorities1 = jwtUserDto.getAuthorities();
            System.out.println(authorities1);
        }
        return jwtUserDto;
    }

}
