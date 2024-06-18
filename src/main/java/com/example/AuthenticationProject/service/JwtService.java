package com.example.AuthenticationProject.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String secretKEY = "YXNkYXNkYXNsa25rbmtqYmFzZmRpdWdhc2RodmJsYmpqYmox";
    public String generateToken(UserDetails user) {
        return Jwts.builder().setSubject(user.getUsername())
                .claim("authorities" , populateAuthorities(user.getAuthorities()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority:authorities){
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join("" , authoritiesSet);
    }

    private Key getSigninKey() {
        byte[] Key = Decoders.BASE64.decode(secretKEY);
        return Keys.hmacShaKeyFor(Key);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigninKey()).build().parseClaimsJws(token).getBody();
    }

    public String extractUserName(String token) {
        return extractClaims(token  , Claims::getSubject);
    }
    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);//we are using this to get a particular Claim
        return claimsResolver.apply(claims);
    }
    public   boolean istokenValid(String token , UserDetails userDetails){
        final String username = extractUserName( token);
        return (username.equals(userDetails.getUsername()));
    }

}
