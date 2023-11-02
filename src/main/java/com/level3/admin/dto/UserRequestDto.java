package com.level3.admin.dto;

import com.level3.admin.entity.DepartmentEnum;
import com.level3.admin.entity.UserRoleEnum;
import lombok.Getter;

@Getter
public class UserRequestDto {
    private String email;
    private String password;
    private DepartmentEnum department;
    private UserRoleEnum role;
}
