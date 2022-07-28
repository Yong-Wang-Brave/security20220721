package com.security.security20220721.Common;

import com.security.security20220721.entity.JwtUserDto;
import com.security.security20220721.entity.LoginProperties;
import com.security.security20220721.entity.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@RequiredArgsConstructor
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    private final LoginProperties loginProperties;
    public void setEnableCache(boolean enableCache){this.loginProperties.setCacheEnable(enableCache);}

    //用户信息缓存
    static Map<String, JwtUserDto> userDtoCache=new ConcurrentHashMap();
    @Override
    public JwtUserDto loadUserByUsername(String username) {
        JwtUserDto jwtUserDto=null;
        UserInfo user;

        try {
            user=new UserInfo();
            user.setEnabled(Boolean.TRUE);
           /* user.setPassword("$2a$10$ZAlZDVATJs7NaI5ALGF.seP5LsOIzGTVpkxrWtYRSP8d0WumzJ2Q6");
            user.setUsername("RUN576882");*/
               user.setPassword("$2a$10$YgwkqFAva7i2TMwrd6v4BOiT5hcL4ab3nEVvkBHOd64oItGOhp9t.");
            user.setUsername("111");
        } catch (Exception e) {
throw new UsernameNotFoundException("",e);        }
        if (user==null) {
            throw new UsernameNotFoundException("");
    }else{
            if (!user.getEnabled()) {
                throw new RuntimeException("账号未激活");
            }
            //

            jwtUserDto=new JwtUserDto(user);

        }
        return jwtUserDto;
    }

}
