package com.fanisi.demo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Log
@Component
public class JWTUtils {

    @Autowired
    JWTAuthorizationFilter jwtAuthorizationFilter;

    private final String secret = System.getenv("JWTSECRET");

    public String generateToken(String username)
    {
        String secretKey = secret;

        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 7000000))
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes()).compact();

        return "Bearer " + token;
    }

    public boolean isTokenValid(String token, UserDetails userDetails)
    {
        Claims claims = Jwts.parserBuilder().setSigningKey(secret.getBytes()).build().parseClaimsJws(token).getBody();
        final String username = claims.getSubject();
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(claims);
    }

    private boolean isTokenExpired(Claims claims)
    {
        Date date = claims.getExpiration();
        return date.before(new Date());
    }
}
