package com.level3.admin.dto.login;

import com.level3.admin.entity.DepartmentEnum;
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

    public UserLoginResponseDto(String token, String username, UserRoleEnum role, String message, DepartmentEnum department) {
        log.info("응답dto: Token - {}, Username - {}, Role - {}, Message - {}, Department - {}", token, username, role, message, department);
        this.username = username;
        this.role = role;
        this.department = department;
        this.message = message;
        this.token = token;
    }
}
