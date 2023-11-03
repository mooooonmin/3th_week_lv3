package com.level3.admin.controller;

import com.level3.admin.dto.signup.UserSignupRequestDto;
import com.level3.admin.dto.signup.UserSignupResponseDto;
import com.level3.admin.entity.User;
import com.level3.admin.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/join") // 어노테이션으로 http 상태코드를 전달할수있지만 직접적으로 설정은 다르게함
    public ResponseEntity<UserSignupResponseDto> join(@Valid @RequestBody UserSignupRequestDto requestDto) throws Exception {
        User user = userService.signUp(requestDto);
        UserSignupResponseDto responseDto = new UserSignupResponseDto(
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getDepartment(),
                "성공적으로 가입되셨습니다"
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    /*@PostMapping("/login")
    public String login(@RequestBody Map<String, String> user) {
        return userService.login(user);
    }*/
}