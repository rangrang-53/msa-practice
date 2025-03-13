package com.example.feignclient.service;

import com.example.feignclient.dto.user.SignUpResponseDTO;
import com.example.feignclient.mapper.UserMapper;
import com.example.feignclient.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    public void signUp(User user) {
        userMapper.insertUser(user);
    }
}
