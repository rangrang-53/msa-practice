package com.example.feignclient.dto.weather;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Items {
    private List<Item> item;
}
