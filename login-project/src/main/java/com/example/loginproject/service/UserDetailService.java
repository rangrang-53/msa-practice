package com.example.loginproject.service;

import com.example.loginproject.mapper.MemberMapper;
import com.example.loginproject.model.Member;
import com.example.loginproject.config.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailService implements UserDetailsService {

    private final MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberMapper.findByUserId(username);

        log.info("로그인 사용자 정보 - UserId: {}, Role: {}, UserName: {}",
                member.getUserId(), member.getRole(), member.getUserName());

        if (member == null) {
            throw new UsernameNotFoundException(username +" not found");
        }
        return CustomUserDetails.builder()
                .member(member)
                .roles(List.of(String.valueOf(member.getRole().name())))
                .build();
    }
}
