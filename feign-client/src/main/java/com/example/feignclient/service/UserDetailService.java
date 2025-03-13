package com.example.feignclient.service;

import com.example.feignclient.config.security.CustomUserDetails;
import com.example.feignclient.mapper.UserMapper;
import com.example.feignclient.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userMapper.findByUserId(username);

        if(user == null){
            throw new UsernameNotFoundException(username + " not found");
        }

        return CustomUserDetails.builder()
                .user(user)
                .build();
    }
}
