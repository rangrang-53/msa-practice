package com.example.memberservice.exception;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(String phoneNumber) {

        super("member not found: " + phoneNumber);
    }
}
