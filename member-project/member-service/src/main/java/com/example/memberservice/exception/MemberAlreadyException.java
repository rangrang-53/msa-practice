package com.example.memberservice.exception;

public class MemberAlreadyException extends RuntimeException {
    public MemberAlreadyException(String name, String phoneNumber) {

        super("member already exists: " + name + " " + phoneNumber);
    }
}
