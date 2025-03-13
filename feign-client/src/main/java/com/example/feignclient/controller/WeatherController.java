package com.example.feignclient.controller;

import com.example.feignclient.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping("/weather")
    public String weather(Model model) {
        model.addAttribute("weather", weatherService.getWeather());
        return "main";
    }

}
