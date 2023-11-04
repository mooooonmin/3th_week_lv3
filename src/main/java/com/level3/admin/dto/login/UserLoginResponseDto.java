package com.level3.admin.dto.login;

import com.level3.admin.entity.DepartmentEnum;
import com.level3.admin.entity.User;
import com.level3.admin.entity.UserRoleEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@ToString
@Slf4j
public class UserLoginResponseDto {

    private String username;
    private UserRoleEnum role;
    private DepartmentEnum department;
    private String message;
    private String token;

    public UserLoginResponseDto(User user, String token) {
        this.username = user.getUsername();
        this.role = user.getRole();
        this.department = user.getDepartment();
        this.message = "로그인에 성공하였습니다";
        this.token = token;
    }
}
