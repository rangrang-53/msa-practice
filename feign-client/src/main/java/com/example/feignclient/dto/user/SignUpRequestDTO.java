package com.example.feignclient.dto.user;

import com.example.feignclient.model.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Builder
public class SignUpRequestDTO {
    private String id;
    private String password;
    private String nickname;

    public User toUser(BCryptPasswordEncoder bCryptPasswordEncoder) {
        return User.builder()
                .id(id)
                .password(bCryptPasswordEncoder.encode(password))
                .nickname(nickname)
                .build();
    }
}
