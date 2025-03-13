package com.example.loginproject.mapper;

import com.example.loginproject.dto.SignInRequestDTO;
import com.example.loginproject.dto.SignInResponseDTO;
import com.example.loginproject.model.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    void savedMember(Member member);
    Member findByUserId(String userId);
}
