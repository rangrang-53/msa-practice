package com.example.feignclient.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignUpResponseDTO {
    private boolean success;
}
