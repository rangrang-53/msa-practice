package com.example.feignclient.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignInRequestDTO {
    private String id;
    private String password;
}
