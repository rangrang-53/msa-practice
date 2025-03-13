package com.example.loginproject.service;

import com.example.loginproject.mapper.MemberMapper;
import com.example.loginproject.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberMapper memberMapper;

    public void signUp(Member member) {
        memberMapper.savedMember(member);
    }
}
