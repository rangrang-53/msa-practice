package com.example.loginproject.config.jwt;

import com.example.loginproject.model.Member;
import com.example.loginproject.type.Role;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;
import javax.xml.crypto.Data;
import java.time.Duration;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TokenProviderTest {

    @Autowired
    TokenProvider tokenProvider;
    @Autowired
    JwtProperties jwtProperties;

    @Test
    void 토큰_생성_테스트() {

        Member member = Member.builder()
                .id(0L)
                .userId("Test")
                .password("Test")
                .userName("Test")
                .role(Role.ROLE_USER)
                .build();

        Duration duration = Duration.ofHours(1);
        String token = tokenProvider.generateToken(member, duration);
        System.out.println(token);
        assertNotNull(token);
    }

    @Test
    void makeToken(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        Date now = new Date();

        Duration duration = Duration.ofMinutes(5);

        Member member = Member.builder()
                .id(0L)
                .userId("Test")
                .password("Test")
                .userName("Test")
                .role(Role.ROLE_USER)
                .build();

        String token = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + duration.toMillis()))
                .setSubject(member.getUserId())
                .claim("id", member.getId())
                .claim("role", member.getRole().name())
                .claim("userName", member.getUserName())
                .signWith(getSecretKey(), SignatureAlgorithm.HS512)
                .compact();

        System.out.println(token);
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = Base64.getDecoder().decode(jwtProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

}