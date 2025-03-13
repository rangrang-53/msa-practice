package com.example.feignclient.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/join")
    public String join() {
        return "signUp";
    }

    @GetMapping("/login")
    public String login() {
        return "signIn";
    }
}
