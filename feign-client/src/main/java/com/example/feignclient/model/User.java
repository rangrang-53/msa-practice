package com.example.feignclient.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {
    private String id;
    private String password;
    private String nickname;
}
