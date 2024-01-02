package com.level3.admin.global.security.Filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.level3.admin.domain.admin.dto.LoginRequestDto;
import com.level3.admin.domain.admin.entity.AdminRoleEnum;
import com.level3.admin.global.dto.SuccessMessageDto;
import com.level3.admin.global.exception.ErrorCode;
import com.level3.admin.global.security.impl.AdminDetailsImpl;
import com.level3.admin.global.security.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/admin/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        log.info("로그인 시도");
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            // AuthenticationManager 가 인증처리
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getEmail(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException {
        log.info("로그인 성공 및 JWT 생성");
        String email = ((AdminDetailsImpl) authResult.getPrincipal()).getAdmin().getEmail();
        AdminRoleEnum role = ((AdminDetailsImpl) authResult.getPrincipal()).getAdmin().getRole();

        String accessToken = jwtUtil.createAccessToken(email, role);
        String refreshToken = jwtUtil.createRefreshToken(email);

        jwtUtil.addJwtToHeader(JwtUtil.ACCESSTOKEN_HEADER, accessToken, response);
        jwtUtil.addJwtToHeader(JwtUtil.REFRESHTOKEN_HEADER, refreshToken, response);

        ObjectMapper objectMapper = new ObjectMapper();
        String AdminJson = objectMapper.writeValueAsString(new SuccessMessageDto("로그인 성공"));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(AdminJson);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        response.setStatus(400);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(ErrorCode.INVALID_EMAIL_PASSWORD.getMessage());
    }

}
