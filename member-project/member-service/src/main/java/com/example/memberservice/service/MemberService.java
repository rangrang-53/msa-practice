package com.example.memberservice.service;

import com.example.memberservice.domain.Member;
import com.example.memberservice.domain.MemberRepository;
import com.example.memberservice.exception.MemberAlreadyException;
import com.example.memberservice.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Iterable<Member> viewMemberList() {
        return memberRepository.findAll();
    }

    public Member viewMemberById(Long id) {
        return memberRepository.findById(id).get();
    }

//    public Member viewMemberByPhoneNumber(String phoneNumber) {
//        return memberRepository.findByPhoneNumber(phoneNumber).
//                orElseThrow(() -> new MemberNotFoundException(phoneNumber));
//    }

    public Member addMemberToInfo(Member member) {
        if(memberRepository.existsByPhoneNumber(member.phoneNumber())){
            throw new MemberNotFoundException(member.phoneNumber());
        }
        return memberRepository.save(member);
    }
}
