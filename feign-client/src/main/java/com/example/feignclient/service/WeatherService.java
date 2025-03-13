package com.example.feignclient.service;


import com.example.feignclient.client.WeatherClient;
import com.example.feignclient.dto.weather.WeatherResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class WeatherService {
    private final WeatherClient weatherClient;
    private final ObjectMapper objectMapper;

    @Value("${weather.service-key}")
    private String serviceKey;

    public WeatherResponse getWeather() {
        int numOfRows = 10;
        int pageNo = 1;
        String dataType = "JSON";
        String baseDate = "20250304";
        String baseTime = "1400";
        int nx = 60;
        int ny = 127;

        String weather = weatherClient.getWeather(
                    serviceKey,
                    numOfRows,
                    pageNo,
                    dataType,
                    baseDate,
                    baseTime,
                    nx,
                    ny
            );
        try {
        return objectMapper.readValue(weather, WeatherResponse.class);
        } catch (Exception e) {
        e.printStackTrace();
        return null;
        }
    }

};
