package com.level3.admin.service;

import com.level3.admin.dto.signup.UserSignupRequestDto;
import com.level3.admin.entity.User;
import com.level3.admin.entity.UserRoleEnum;
import com.level3.admin.jwt.JwtUtil;
import com.level3.admin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    // 회원가입만 처리함 -> 로그인 관련은 다른 클래스에 구현

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public User signUp(UserSignupRequestDto requestDto) {

        // 사용자이름 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(requestDto.getUsername());
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 이메일 중복 확인
        String email = requestDto.getEmail();
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        // 비밀번호 인코딩
        String password = passwordEncoder.encode(requestDto.getPassword());


        // 사용자 ROLE 확인
        // 기본적으로 staff -> 인증하면 manager
        UserRoleEnum role = UserRoleEnum.STAFF;
        if (requestDto.isAdmin()) {
            if (ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                role = UserRoleEnum.MANAGER;
            } else {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
        }

        // 사용자 등록
        User user = User.builder()
                .username(requestDto.getUsername())
                .password(password)
                .email(email)
                .role(role) // 회원가입요청시 사용자가 직접 권한을 지정하지 않음, 서버측에서 결정 - 토큰값으로 결정하나봄
                .department(requestDto.getDepartment()) // 이건 회원가입시 직접 입력이라서
                .build();

        return userRepository.save(user);

    }
}

