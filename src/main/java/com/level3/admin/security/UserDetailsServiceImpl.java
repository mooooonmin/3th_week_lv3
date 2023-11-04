package com.level3.admin.security;

import com.level3.admin.dto.login.UserLoginRequestDto;
import com.level3.admin.dto.login.UserLoginResponseDto;
import com.level3.admin.entity.User;
import com.level3.admin.jwt.JwtUtil;
import com.level3.admin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // 입력받은 사용자의 이름을 통해서 조회 -> 이메일로 인증하는데 이름을 찾느낟?
    // 해당 사용자 정보 -> userDetails로 변환해서 반환
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Fetching user details for: {}", email);  // 로그 추가
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("User not found: {}", email);  // 로그 추가
                    return new UsernameNotFoundException("Not Found " + email);
                });

        log.info("User details loaded successfully for: {}", email);  // 로그 추가
        return new UserDetailsImpl(user);
    }

    // 로그인 요청 처리
    // 이메일 입력받아서 -> 사용자 조회 -> 비밀번호 검증 -> jwt 토큰 생성 -> 응답 반환
    public UserLoginResponseDto login(UserLoginRequestDto requestDto) {
        log.info("login 메서드 호출됨: {}", requestDto.getEmail());

        // 사용자 조회
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> {
                    log.error("사용자 이메일 몾찾음: {}", requestDto.getEmail());
                    return new IllegalArgumentException("존재하지 않는 사용자입니다.");
                });

        // 비밀번호 검증
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            log.error("비밀번호 불일치: {}", requestDto.getEmail());
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        log.info("이메일에 맞는 비밀번호 확인: {}", requestDto.getEmail());

        // 토큰 생성
        String token = jwtUtil.createToken(user.getEmail(), user.getRole());
        log.info("사용자를 위한 토큰생성: {}", requestDto.getEmail());

        // 응답 객체 생성 및 반환
        return new UserLoginResponseDto(token, user.getEmail(), user.getRole(), "로그인에 성공하였습니다.", user.getDepartment());
    }
}