package com.level3.admin.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    // JWT 생성 -> 해당 토큰을 이용해 사용자를 인증하는 과정

    @Value("${jwt.secret.key}")
    private String secretKey; // jwt를 암호화하는데 사용되는 비밀키 -> 프로퍼티스파일에 저장

    private long tokenValidTime = 60 * 60 * 1000L;  // 60분(토큰의 유효기간)
    private final UserDetailsService userDetailsService;  // 사용자의 정보를 불러오는 서비스

    @PostConstruct // 객체가 생성된 후 초기화, secretKey 를 Base64로 인코딩합니다.
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT 토큰 생성
    public String createToken(String userPk, List<String> roles) {
        // 사용자 고유값과 사용자 역할을 통해 jwt 생성
        // claims 객체에 사용자 식별자와 역할 정보를 넣고, 발행시간, 만료시간 암호화 알고리즘을 설정하여 토큰 생성
        Claims claims = Jwts.claims().setSubject(userPk); // JWT payload 에 저장되는 정보단위
        claims.put("roles", roles); // 정보는 key/value 쌍으로 저장됩니다.

        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘
                // signature 에 들어갈 secret 값 세팅
                .compact();
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        // token 받아서 -> Authentication 객체 반환
        // 토큰 -> 사용자 식별자 추출 -> userDetails 불러옴
        // UsernamePasswordAuthenticationToken 생성해 인증 정보 반환
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 값을 가져옴
    // "X-AUTH-TOKEN" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try { // 유효하면 T, 아니면 F
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}