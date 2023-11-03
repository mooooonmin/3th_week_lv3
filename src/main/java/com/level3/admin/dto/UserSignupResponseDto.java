package com.level3.admin.dto;

public class UserSignupResponseDto {
    private String username;
    private String email;
    private String role;
    private String depertment;
    private String message;

    public UserSignupResponseDto(String username, String email, String role, String department, String message) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.depertment = department;
        this.message = message;
    }
}
