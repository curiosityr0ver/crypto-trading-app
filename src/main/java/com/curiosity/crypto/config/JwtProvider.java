package com.curiosity.crypto.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class JwtProvider {
    private static final SecretKey key =Keys.hmacShaKeyFor(JwtConstant.JWT_SECRET_KEY.getBytes());

    public static String generateToken(Authentication auth){
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        String roles = populateAuthorities(authorities);

        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+86400000))
                .claim("email", auth.getName())
                .claim("roles", roles)
                .signWith(key)
                .compact();
    }

    private static String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> roles = new HashSet<String>();
        for (GrantedAuthority grantedAuthority : authorities) {
            roles.add(grantedAuthority.getAuthority());
        }
        return String.join(",", roles);
    }

    public static String getEmailFromToken(String token){
        token = token.substring(JwtConstant.JWT_SECRET_KEY.length());
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

        return (String) claims.get("email");
    }
}
