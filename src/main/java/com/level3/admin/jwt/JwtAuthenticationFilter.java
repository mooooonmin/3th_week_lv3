package com.level3.admin.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // 스프링 시큐리티의 OncePerRequestFilter 상속받은 필터
    // HTTP 요청이 들어올때 마다 한번씩 실행
    // JWT 토큰을 확인하고 인증 정보를 설정하는 역할
    private final JwtTokenProvider jwtAuthenticationProvider; // jwt 토큰의 생성, 검증

    public JwtAuthenticationFilter(JwtTokenProvider provider) {
        jwtAuthenticationProvider = provider; // 생성자를 주입받아 초기화
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Http 요청이 들어올 때마다 실행 -> jwt 토큰을 확인하고 인증 정보를 설정하는 역할

        String token = jwtAuthenticationProvider.resolveToken(request); // 토큰 추출

        if (token != null && jwtAuthenticationProvider.validateToken(token)) {// 추출한 토큰이 유효한지 검증
            Authentication authentication = jwtAuthenticationProvider.getAuthentication(token);
            // 유효한 토큰의 경우, 해당 토큰을 기반으로 인증 객체 생성

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }   // 생성한 인증 객체를 -> SecurityContext 설정, 이후 요청 처리 과정에서 인증 정보를 사용할 수 있도록함

        filterChain.doFilter(request, response); // 필터로 요청과 응답을 전달
        // 이 필터를 통해, 요청마다 jwt 토큰이 유효한지 확인하고, 유효한 토큰의 경우 인증 정보를 SecurityContext 설정
        // 이후 요청 처리 과정에서 사용자 인증을 유지

    }
}