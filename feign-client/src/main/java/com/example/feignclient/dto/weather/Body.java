package com.example.feignclient.dto.weather;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Body {
    private String dataType;
    private Items items;
}
