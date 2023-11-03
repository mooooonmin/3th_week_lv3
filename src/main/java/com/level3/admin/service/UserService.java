package com.level3.admin.service;

import com.level3.admin.dto.UserSignupRequestDto;
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

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public void signUp(UserSignupRequestDto requestDto) {
        String email = requestDto.getEmail();
        // 요청받은 비밀번호를 인코딩해서 넣기
        String password = passwordEncoder.encode(requestDto.getPassword())
    }

    // 회원 중복 확인
    Optional<User> checkUsername = userRepository.findByUsername(username);
        if(checkUsername.isPresent())

    {
        throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
    }

    // email 중복확인
    String email = requestDto.getEmail();
    Optional<User> checkEmail = userRepository.findByEmail(email);
        if(checkEmail.isPresent())

    {
        throw new IllegalArgumentException("중복된 Email 입니다.");
    }

    // 사용자 ROLE 확인
    UserRoleEnum role = UserRoleEnum.STAFF;
        if(requestDto.isAdmin())

    {
        if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
            throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
        }
        role = UserRoleEnum.MANAGER;
    }

    // 사용자 등록
    User user = new User(username, password, email, role);
        userRepository.save(user);
}

