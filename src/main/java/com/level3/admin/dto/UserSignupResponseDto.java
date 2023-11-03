package com.level3.admin.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserSignupResponseDto {
    private String username;
    private String email;
    private String role;
    private String department;
    private String message;

    public UserSignupResponseDto(String username, String email, String role, String department, String message) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.department = department;
        this.message = message;
    }
}
