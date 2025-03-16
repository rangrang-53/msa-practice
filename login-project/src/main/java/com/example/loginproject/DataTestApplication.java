package com.example.loginproject;

import com.example.loginproject.model.Article;
import com.example.loginproject.model.Member;
import com.example.loginproject.service.BoardService;
import com.example.loginproject.service.MemberService;
import com.example.loginproject.type.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class DataTestApplication implements CommandLineRunner {
    @Autowired
    private MemberService memberService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;  // 비밀번호 암호화를 위한 BCryptPasswordEncoder

    public static void main(String[] args) {
        SpringApplication.run(DataTestApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Member 데이터 100만건 삽입
        for (int i = 1; i <= 1000000; i++) {
            String rawPassword = "password" + i;  // 원본 비밀번호
            String encodedPassword = passwordEncoder.encode(rawPassword);  // 비밀번호 암호화

            Member member = Member.builder()
                    .userId("user" + i)
                    .password(encodedPassword)  // 암호화된 비밀번호 저장
                    .userName("User " + i)
                    .role(Role.valueOf("ROLE_USER"))
                    .build();
            memberService.signUp(member);  // 회원 가입
        }

        // Article 데이터 100만건 삽입
        for (int i = 1; i <= 1000000; i++) {
            Article article = Article.builder()
                    .userId("user" + i)
                    .title("Article " + i)
                    .content("This is the content for article " + i)
                    .build();
            boardService.saveArticle("user" + i, "Article " + i, "This is the content for article " + i, null);  // 파일은 null로 설정
        }
    }
}
