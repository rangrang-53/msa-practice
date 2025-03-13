package com.example.feignclient.mapper;

import com.example.feignclient.dto.user.SignInResponseDTO;
import com.example.feignclient.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    void insertUser(User user);
    User findByUserId(String id);
}
