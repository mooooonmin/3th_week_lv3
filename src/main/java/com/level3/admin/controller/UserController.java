package com.level3.admin.controller;

import com.level3.admin.dto.login.UserLoginRequestDto;
import com.level3.admin.dto.login.UserLoginResponseDto;
import com.level3.admin.dto.signup.UserSignupRequestDto;
import com.level3.admin.dto.signup.UserSignupResponseDto;
import com.level3.admin.security.UserDetailsServiceImpl;
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
    private final UserDetailsServiceImpl userServiceImpl;


    @PostMapping("/join")
    public ResponseEntity<UserSignupResponseDto> join(@Valid @RequestBody UserSignupRequestDto requestDto) throws Exception {
        UserSignupResponseDto responseDto = userService.signUp(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(@Valid @RequestBody UserLoginRequestDto requestDto) throws Exception {
        UserLoginResponseDto responseDto = userServiceImpl.login(requestDto);
        return ResponseEntity.ok(responseDto);
    }
}