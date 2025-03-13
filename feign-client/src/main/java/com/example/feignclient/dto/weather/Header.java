package com.example.feignclient.dto.weather;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Header {
    private String resultCode;
    private String resultMsg;
}
