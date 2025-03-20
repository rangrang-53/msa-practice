package com.example.memberservice.controller;

import com.example.memberservice.domain.Member;
import com.example.memberservice.domain.MemberRepository;
import com.example.memberservice.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public Iterable<Member> getMemberList() {
        return memberService.viewMemberList();
    }

    @GetMapping("/{id}")
    public Member getMember(@PathVariable Long id) {
        return memberService.viewMemberById(id);
    }

    @PostMapping
    public Member createMember(@Valid @RequestBody Member member) {
        return memberService.addMemberToInfo(member);
    }
}
