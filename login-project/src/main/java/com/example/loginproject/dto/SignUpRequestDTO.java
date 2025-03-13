package com.example.loginproject.dto;

import com.example.loginproject.model.Member;
import com.example.loginproject.type.Role;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@ToString
public class SignUpRequestDTO {
    private String userId;
    private String password;
    private String userName;
    private Role role;


    //비밀번호 암호화
    public Member toMember(BCryptPasswordEncoder passwordEncoder) {
        return Member.builder()
                .userId(userId)
                .password(passwordEncoder.encode(password))
                .userName(userName)
                .role(role)
                .build();

    }
}
