package com.example.loginproject.dto;

import com.example.loginproject.type.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoResponseDTO {

    private Long id;
    private String userId;
    private String userName;
    private Role role;
}
