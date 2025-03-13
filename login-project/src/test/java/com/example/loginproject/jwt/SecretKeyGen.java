package com.example.loginproject.jwt;

import org.junit.jupiter.api.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

class SecretKeyGen {

    @Test
    void HS512_생성() {
        Mac sha512_HMAC = null;
        String data = "Spring boot basic board 2";
        String secretKey = "256-bit-secret"; // 비밀키 설정
        try {
            sha512_HMAC = Mac.getInstance("HmacSHA512");
            SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA512");
            sha512_HMAC.init(keySpec);
            byte[] macData = sha512_HMAC.doFinal(data.getBytes("UTF-8"));
            String secret_key = Base64.getEncoder().encodeToString(macData);

            System.out.println(secret_key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}