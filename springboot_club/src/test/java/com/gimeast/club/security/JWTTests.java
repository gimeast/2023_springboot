package com.gimeast.club.security;

import com.gimeast.club.security.util.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JWTTests {

    private JWTUtil jwtUtil;

    @BeforeEach
    public void testBefore() {
        System.out.println("testBefore...........");
        jwtUtil = new JWTUtil();
        System.out.println("생성");
    }

    @Test
    void testEncode() throws Exception {
        String email = "user95@gmail.com";
        String str = jwtUtil.generateToken(email);
        System.out.println(str);
    }

    @Test
    void testValidate() throws Exception {

        String email = "user95@gmail.com";
        String str = jwtUtil.generateToken(email);

        Thread.sleep(5000);

        String resultEmail = jwtUtil.validateAndExtract(str);
        System.out.println(resultEmail);

    }
}
