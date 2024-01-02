package com.level3.admin.global.security.Filter;

import com.level3.admin.global.exception.CustomException;
import com.level3.admin.global.exception.ErrorCode;
import com.level3.admin.global.security.impl.AdminDetailsServiceImpl;
import com.level3.admin.global.security.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.URLDecoder;

@Slf4j(topic = "인가 확인")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthorizationFilter.class);
    private final JwtUtil jwtUtil;
    private final AdminDetailsServiceImpl adminDetailsService;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, AdminDetailsServiceImpl adminDetailsService) {
        this.jwtUtil = jwtUtil;
        this.adminDetailsService = adminDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        String path = req.getRequestURI();

        // 로그인 및 회원가입 요청 시 필터 적용을 건너뛰기
        if (path.equals("/api/admin/login") || path.equals("/api/admin/signup")) {
            filterChain.doFilter(req, res);
            return;
        }

        try {
            if (req.getHeader(JwtUtil.ACCESSTOKEN_HEADER) == null){
                log.info("오류발생");
                throw new CustomException(ErrorCode.NOT_FOUND_TOKEN);
            }
            String token = URLDecoder.decode(req.getHeader(JwtUtil.ACCESSTOKEN_HEADER), "UTF-8");

            if (StringUtils.hasText(token) && token != null) {

                String tokenValue = jwtUtil.substringToken(token);
                logger.error("토큰 확인용 : " + tokenValue);
                jwtUtil.jwtValidateToken(tokenValue);

                Claims info = jwtUtil.getUserInfoFromToken(tokenValue);

                try {
                    setAuthentication(info.getSubject());
                } catch (Exception e) {
                    logger.error("-->인증오류<--");
                }
            }
        } catch (Exception e) {
            req.setAttribute("exception", e);
        }
        logger.error(req.getRequestURI());
        filterChain.doFilter(req, res);
    }

    // 인증 처리
    public void setAuthentication(String email) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = null;
        try {
            authentication = createAuthentication(email);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String email) {
        UserDetails userDetails = adminDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
