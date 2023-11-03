package com.level3.admin.controller;

import com.level3.admin.dto.UserSignupRequestDto;
import com.level3.admin.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController {

    private final UserService userService;
    //private final UserRepository userRepository;

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.OK)
    public Long join(@Valid @RequestBody UserSignupRequestDto requestDto) throws Exception {
        return userService.signUp(requestDto);
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> user)2 {
        return userService.login(user);
    }
}