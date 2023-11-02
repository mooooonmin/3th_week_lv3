package com.level3.admin.security;


import com.level3.admin.dto.UserRequestDto;
import com.level3.admin.entity.User;
import com.level3.admin.repository.UserRepository;
import com.level3.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Transactional(readOnly = true) // 값을 변경하지는 않고 읽기만 하기
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public Long signUp(UserRequestDto requestDto) throws Exception {

        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new Exception("이미 존재하는 이메일입니다");
        }

        if (!requestDto.getPassword().equals(requestDto.getCheckedPassword())) {
            throw new Exception("비밀번호가 일치하지 않습니다");
        }

        //set을 안쓰는 방법이 있나?
        //User user = userRepository.save(requestDto.toEntity());
        //user.encodePassword(passwordEncoder);
        //user.addUserAuthority();

        // dtd -> entity
        User user = requestDto.toEntity();

        // 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // 사용자 권한 설정 및 저장
        user.addUserAuthority();
        userRepository.save(user);

        return user.getUserId();
    }

    @Override
    public String login(Map<String, String> users) {

        User user = userRepository.findByEmail(users.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 Email 입니다."));

        String password = users.get("password");
        if (!user.checkPassword(passwordEncoder, password)) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        List<String> roles = new ArrayList<>();
        roles.add(user.getRole().name());

        return jwtTokenProvider.createToken(user.getUserId(), roles);
}


