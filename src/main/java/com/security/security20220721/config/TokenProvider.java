package com.security.security20220721.config;

import com.google.common.collect.Maps;
import com.security.security20220721.entity.JwtUserDto;
import com.security.security20220721.utils.SecurityProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.security.Security;
import java.util.*;

@Slf4j
@Component
//public class TokenProvider implements InitializingBean {
public class TokenProvider{
    private SecurityProperties properties;
    public static final String AUTHORITIES_KEY = "user";
    public static final String AUTHORITIES_ROLE = "role";
    private JwtParser jwtParser;
    private JwtBuilder jwtBuilder;

    public TokenProvider(SecurityProperties properties) {
        this.properties = properties;
    }

    //benn初始化操作的方式一   @PostConstruct
@PostConstruct
public void Init(){
    byte[] keyBytes = Decoders.BASE64.decode(properties.getBase64Secret());
    Key key = Keys.hmacShaKeyFor(keyBytes);
    jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
    jwtBuilder = Jwts.builder().signWith(key, SignatureAlgorithm.HS512);
}
    //benn初始化操作的方式二     implements InitializingBean 重写 afterPropertiesSet方法
    //先把令牌加载进去方便后面使用。
 /*   @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(properties.getBase64Secret());
        Key key = Keys.hmacShaKeyFor(keyBytes);
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
        jwtBuilder = Jwts.builder().signWith(key, SignatureAlgorithm.HS512);

    }*/


   public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
       Object roles = claims.get("role");
      String  role = object2Map(roles);
       // 角色集合
       List<GrantedAuthority> authorities = new ArrayList<>();
       // 角色必须以`ROLE_`开头，数据库中没有，则在这里加
       authorities.add(new SimpleGrantedAuthority(role));
       User principal = new User(claims.getSubject(), "******", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);


    }
    /**
     * Object转换为Map<Integer, String>格式
     *
     * @param obj Excel的行数据
     * @return List数据
     */
    private String object2Map(Object obj) {
        Map<String, String> map = Maps.newHashMap();
        List<LinkedHashMap<String,String>> result = new ArrayList<>();
        if (obj instanceof ArrayList<?>) {
            for (Object o : (List<?>) obj) {
                result.add((LinkedHashMap) o);
            }
        }
        String role=result.get(0).get("authority");

        return role;
    }
    public Claims getClaims(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public String getToken(HttpServletRequest request) {
        final String requestHeader = request.getHeader(properties.getHeader());
        if (requestHeader != null && requestHeader.startsWith(properties.getTokenStartWith())) {
            return requestHeader.substring(7);
        }
        return null;
    }

    //创建token 设置永不过期  token时间的有效性转移到redis
    public String createToken(Authentication authentication){
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        instance.add(Calendar.SECOND,Integer.parseInt(properties.getTokenValidityInSeconds()+""));
        HashMap hashMap = new HashMap();
        JwtUserDto principal = (JwtUserDto)authentication.getPrincipal();
        hashMap.put("role",principal.getAuthorities());
        return jwtBuilder.setId("uuid")
                .claim(AUTHORITIES_KEY,authentication.getName())
                .claim(AUTHORITIES_ROLE, ((JwtUserDto) authentication.getPrincipal()).getAuthorities())
                .setHeader(hashMap)
                .setSubject(authentication.getName())
                .setExpiration(instance.getTime())
                .compact();
    }

}
