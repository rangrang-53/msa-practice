package com.example.feignclient.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignInResponseDTO {
    private boolean success;
    private String id;
    private String nickname;
}
