package com.level3.admin.global.security.jwt;

import com.level3.admin.domain.admin.entity.AdminRoleEnum;
import com.level3.admin.global.exception.CustomException;
import com.level3.admin.global.exception.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    public static final String ACCESSTOKEN_HEADER = "Authorization"; // Accesstoken Header KEY 값
    public static final String REFRESHTOKEN_HEADER = "Refreshtoken"; // Refreshtoken Header KEY 값
    public static final String AUTHORIZATION_KEY = "auth"; // 사용자 권한 값의 KEY
    public static final String BEARER_PREFIX = "Bearer "; // Token 식별자

    // 토큰 만료시간
    private final long ACCESSTOKEN_TIME = 15 * 60 * 1000L; // 15분
    private final long REFRESHTOKEN_TIME = 60 * 60 * 1000L; // 60분 -- > 1시간

    @Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey

    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    public static final Logger logger = LoggerFactory.getLogger("JWT 관련 로그");

    @PostConstruct
    // @PostConstruct : 딱 한번 받아와야 하는 값을 사용 할때마다 요청을 새로 호출하는 실수를 방지하기 위해 사용
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // 엑세스 토큰 생성
    public String createAccessToken(String email, AdminRoleEnum role) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(email)
                        .claim(AUTHORIZATION_KEY, role)
                        .setExpiration(new Date(date.getTime() + ACCESSTOKEN_TIME))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    // 리프세쉬 토큰 생성
    public String createRefreshToken(String email) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(email)
                        .setExpiration(new Date(date.getTime() + REFRESHTOKEN_TIME))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    // JWT 헤더로 전달
    public void addJwtToHeader(String header, String token, HttpServletResponse res) {
        try {
            token = URLEncoder.encode(token, "utf-8").replaceAll("\\+", "%20");
            res.addHeader(header, token);

        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage() + "헤더로 토큰 전달");
        }
    }

    // JWT 토큰 substring
    public String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        logger.error("Not Found Token");
        throw new CustomException(ErrorCode.INVALID_TOKEN);
    }

    // 토큰 검증
    public void jwtValidateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            logger.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token, 만료된 JWT token 입니다.");
            throw new CustomException(ErrorCode.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }

    //리프레쉬 토큰 검증
    public void refreshValidateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            logger.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token, 만료된 JWT token 입니다.");
            throw new CustomException(ErrorCode.EXPIRED_REFRESH_TOKEN);
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }
    }

    // 토큰에서 사용자 정보 가져오기
    // 키 확인작업
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}