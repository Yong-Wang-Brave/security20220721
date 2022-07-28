package com.security.security20220721.config;

import com.security.security20220721.utils.SecurityProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.security.Security;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

@Slf4j
@Component
public class TokenProvider implements InitializingBean {
    private SecurityProperties properties;
    public static final String AUTHORITIES_KEY = "user";
    private JwtParser jwtParser;
    private JwtBuilder jwtBuilder;

    public TokenProvider(SecurityProperties properties) {
        this.properties = properties;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(properties.getBase64Secret());
        Key key = Keys.hmacShaKeyFor(keyBytes);
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
        jwtBuilder = Jwts.builder().signWith(key, SignatureAlgorithm.HS512);

    }


   public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        User principal = new User(claims.getSubject(), "******", new ArrayList<>());
        return new UsernamePasswordAuthenticationToken(principal, token, new ArrayList<>());


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

        return jwtBuilder.setId("")
                .claim(AUTHORITIES_KEY,authentication.getName())
                .setSubject(authentication.getName())
                .setExpiration(instance.getTime())
                .compact();
    }

}
