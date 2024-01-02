package com.level3.admin.domain.admin.controller;

import com.level3.admin.domain.admin.dto.AdminRequestDto;
import com.level3.admin.domain.admin.service.AdminService;
import com.level3.admin.global.dto.SuccessMessageDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/signup")
    public SuccessMessageDto AdminSignup(@Valid @RequestBody AdminRequestDto adminRequestDto) {
        adminService.adminSignup(adminRequestDto);
        return new SuccessMessageDto("관리자 회원가입에 성공하셨습니다!");
    }
}
