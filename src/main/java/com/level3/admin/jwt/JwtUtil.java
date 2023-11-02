package com.level3.admin.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;

@Component
public class JwtUtil {
    // Header key 값
    public static final String AUTHORIZATION_HEADER = "Authorization";
    // 사용자 권한 값의 key
    public static final String AUTHORIZATION_KEY = "auth";
    // token 식별자
    public static final String BEARER_PREFIX = "Bearer "; // Jwt 혹은 OAuth에 대한 토큰 사용

    // 토큰 만료시간
    private final long TOKEN_TIME = 60 * 60 * 1000L; // 60분

    @Value("${jwt.secret.key}") // base64 Encode -> secretkey
    private String secretKey;
    private Key key; // decode 된 secretkey를 담는 객체
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;


    // 로그 설정
    public static final Logger logger = LoggerFactory.getLogger("JWT 관련 로그");

    @PostConstruct // 딱 하넌만 받아오면 되는 값을 사용 할 때마다 요청을 새로 호출하는 실수를 방지하기 위해 사용
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Key.hmacShaKeyFor(bytes);
    }
}
