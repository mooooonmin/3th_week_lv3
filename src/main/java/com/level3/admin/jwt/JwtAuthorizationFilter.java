package com.level3.admin.jwt;

import com.level3.admin.security.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

        String tokenValue = jwtUtil.getTokenFromRequest(req);

        // TODO 회원가입 및 기타 예외 경로
        String requestURI = req.getRequestURI();
        if ("/api/user/join".equals(requestURI)) {
            log.info("Request URI: {}", requestURI);
            // 회원가입 경로면 필터를 건너뛰고 다음 필터로 넘어간다
            filterChain.doFilter(req, res);
            return;
        }

        if (StringUtils.hasText(tokenValue)) {

            // JWT 토큰 substring
            try {
                tokenValue = jwtUtil.substringToken(tokenValue);
            } catch (IllegalArgumentException e) {
                log.error("Error: {}", e.getMessage());
                filterChain.doFilter(req, res);
                return;
            }
            log.info(tokenValue);


            if (!jwtUtil.validateToken(tokenValue)) {
                log.error("Token validation failed for token: {}", tokenValue);
                return;
            }

            Claims info = jwtUtil.getUserInfoFromToken(tokenValue);

            try {
                setAuthentication(info.getSubject());
                log.info("Authentication set for user: {}", info.getSubject());
            } catch (Exception e) {
                log.error("Error setting authentication: {}", e.getMessage());
                return;
            }
        }

        filterChain.doFilter(req, res);
    }

    // 인증 처리
    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
