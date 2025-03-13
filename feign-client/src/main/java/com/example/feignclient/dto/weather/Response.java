package com.example.feignclient.dto.weather;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
    private Header header;
    private Body body;
}
