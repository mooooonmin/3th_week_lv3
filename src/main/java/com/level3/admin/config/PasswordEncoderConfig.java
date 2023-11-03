package com.level3.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 예를 들어, 나중에 다른 암호화 방식을 사용하고 싶으면 여기만 변경하면 됩니다.
        return new BCryptPasswordEncoder();
    }
}