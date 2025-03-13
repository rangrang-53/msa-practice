package com.example.loginproject.model;

import com.example.loginproject.type.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Member {
    private Long id;
    private String userId;
    private String password;
    private String userName;
    private Role role;
}
