package com.example.loginproject.config.jwt;

import com.example.loginproject.model.Member;
import com.example.loginproject.service.UserDetailService;
import com.example.loginproject.type.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenProvider {

    private final JwtProperties jwtProperties;


    public String generateToken(Member member, Duration expiredAt) {
        Date now = new Date();
        return makeToken(member, new Date(now.getTime() + expiredAt.toMillis()));
    }

    public Member getTokenDetails(String token) {
        Claims claims = getClaims(token);

        return Member.builder()
                .id(claims.get("id", Long.class))
                .userId(claims.getSubject())
                .userName(claims.get("userName", String.class))
                .role(
                        Role.valueOf(claims.get("role", String.class))
                )
                .build();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);

        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(claims.get("role",String.class))
        );

        UserDetails userDetails = new User(claims.getSubject(), "" , authorities);

        return new UsernamePasswordAuthenticationToken(userDetails,token,authorities);
    }

    public int validToken(String token) {
        try {
            getClaims(token);
            return 1;
        } catch (ExpiredJwtException e){
            log.info("토큰 만료");
            return 2;
        } catch (Exception e) {
            System.out.println("Token 복호화 에러 : " + e.getMessage());
            return 3;
        }

    }

    private String makeToken(Member member, Date expired) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE,Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(expired)
                .setSubject(member.getUserId())
                .claim("id",member.getId())
                .claim("role",member.getRole().name())
                .claim("userName",member.getUserName())
                .signWith(getSecretKey(),SignatureAlgorithm.HS512)
                .compact();

    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = Base64.getDecoder().decode(jwtProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
