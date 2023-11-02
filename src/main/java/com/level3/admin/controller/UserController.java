package com.level3.admin.controller;

import com.level3.admin.dto.UserRequestDto;
import com.level3.admin.repository.UserRepository;
import com.level3.admin.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.OK)
    public Long join(@Valid @RequestBody UserRequestDto requestDto) throws Exception {
        return userService.signUp(requestDto);
    }
}
