package com.level3.admin.service;

import com.level3.admin.dto.signup.UserSignupRequestDto;
import com.level3.admin.dto.signup.UserSignupResponseDto;
import com.level3.admin.entity.DepartmentEnum;
import com.level3.admin.entity.User;
import com.level3.admin.entity.UserRoleEnum;
import com.level3.admin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
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
            log.info("isAdmin->true, adminToken 체크");
            String ADMIN_TOKEN = "asdfasdfasdfasdf";
            if (ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                log.info("Admin token 맞을, 부서 확인.");
                // 부서가 DEVELOPMENT 또는 CURRICULUM인 경우만 MANAGER 권한 부여
                DepartmentEnum department = requestDto.getDepartment();
                if (department == DepartmentEnum.DEVELOPMENT || department == DepartmentEnum.CURRICULUM) {
                    log.info("부서까지 맞으면 manager");
                    role = UserRoleEnum.MANAGER;
                } else {
                    log.warn("Invalid department for MANAGER role. Setting role to STAFF.");
                    message = "MANAGER 권한은 DEVELOPMENT 또는 CURRICULUM 부서에서만 가능합니다.";
                }
            } else {
                log.warn("Admintoken 틀릴 때, STAFF");
                message = "토큰이 틀려서 STAFF로 가입됩니다.";
            }
        } else {
            log.info("isAdmin -> false. STAFF");
        }

        // 사용자 등록
        User user = User.builder()
                .username(requestDto.getUsername())
                .password(password)
                .email(email)
                .role(role)
                .department(requestDto.getDepartment())
                .admin(requestDto.isAdmin())
                .build();

        userRepository.save(user);

        return new UserSignupResponseDto(
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getDepartment(),
                user.isAdmin(),
                message);

    }
}

