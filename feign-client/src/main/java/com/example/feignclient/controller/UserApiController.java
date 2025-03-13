package com.example.feignclient.controller;

import com.example.feignclient.dto.user.SignUpRequestDTO;
import com.example.feignclient.dto.user.SignUpResponseDTO;
import com.example.feignclient.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/join")
    public SignUpResponseDTO join(@RequestBody SignUpRequestDTO signUpRequestDTO) {

        userService.signUp(signUpRequestDTO.toUser(bCryptPasswordEncoder));
        return SignUpResponseDTO.builder().build();
    }
}
