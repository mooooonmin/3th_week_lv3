package com.level3.admin.service;

import com.level3.admin.dto.signup.UserSignupRequestDto;
import com.level3.admin.dto.signup.UserSignupResponseDto;
import com.level3.admin.entity.User;
import com.level3.admin.entity.UserRoleEnum;
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

    public UserSignupResponseDto signUp(UserSignupRequestDto requestDto) {

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


        // 사용자 ROLE 확인 및 메시지
        UserRoleEnum role = UserRoleEnum.STAFF;
        String message = "성공적으로 가입되셨습니다."; // 기본 메세지

        if (requestDto.isAdmin()) {
            String ADMIN_TOKEN = "asdfasdfasdfasdf";
            if (ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                role = UserRoleEnum.MANAGER;
            } else {
                message = "토큰이 틀려서 STAFF로 가입됩니다."; // 토큰틀리면 메세지 반환
            }
        }

        // 사용자 등록
        User user = User.builder()
                .username(requestDto.getUsername())
                .password(password)
                .email(email)
                .role(role) // 회원가입요청시 사용자가 직접 권한을 지정하지 않음, 서버측에서 결정 - 어드민토큰값으로 결정
                .department(requestDto.getDepartment()) // 이건 회원가입시 직접 입력이라서
                .build();

        userRepository.save(user);

        return new UserSignupResponseDto(user.getUsername(), user.getEmail(), user.getRole(), user.getDepartment(), message);

    }
}

