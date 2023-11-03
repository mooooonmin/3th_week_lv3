package com.level3.admin.dto.login;

import com.level3.admin.entity.DepartmentEnum;
import com.level3.admin.entity.UserRoleEnum;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserLoginResponseDto {

    private String username;
    private UserRoleEnum role;
    private DepartmentEnum department;
    private String message;
    private String token;

    public UserLoginResponseDto(String token, String username, UserRoleEnum role, String message, DepartmentEnum department) {
        this.username = username;
        this.role = role;
        this.department = department;
        this.message = message;
        this.token = token;
    }
}
