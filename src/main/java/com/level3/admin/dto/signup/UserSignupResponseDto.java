package com.level3.admin.dto.signup;

import com.level3.admin.entity.DepartmentEnum;
import com.level3.admin.entity.UserRoleEnum;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserSignupResponseDto {
    private String username;
    private String email;
    private UserRoleEnum role;
    private DepartmentEnum department;
    private boolean admin;
    private String message;

    public UserSignupResponseDto(String username,
                                 String email,
                                 UserRoleEnum role,
                                 DepartmentEnum department,
                                 boolean admin,
                                 String message) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.department = department;
        this.admin = admin;
        this.message = message;
    }
}
