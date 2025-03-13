package com.example.feignclient.dto.weather;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {
    private String baseDate;
    private String baseTime;
    private String category;
    private int nx;
    private int ny;
    private String obsrValue;
}
