package com.level3.admin.dto;

import com.level3.admin.entity.DepartmentEnum;
import com.level3.admin.entity.User;
import com.level3.admin.entity.UserRoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    // 로그인에 관련된 유효성검사

    @Email(message = "형식에 맞게 입력하세요")
    @NotBlank(message = "이메일은 비어있을 수 없습니다")
    private String email;

    @NotBlank(message = "비밀번호는 비어있을 수 없습니다")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,15}$",
            message = "비밀번호는 8~15자리면서 알파벳, 숫자, 특수만자를 포함해야합니다")
    private String password;

    private String checkedPassword;

    private DepartmentEnum department;

    private UserRoleEnum role;

    @Builder
    public User toEntity(){
        return User.builder()
                .email(email)
                .password(password)
                .department(department)
                .role(role)
                .build();
    }
}
