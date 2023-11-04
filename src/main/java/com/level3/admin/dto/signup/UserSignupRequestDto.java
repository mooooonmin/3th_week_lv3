package com.level3.admin.dto.signup;

import com.level3.admin.entity.DepartmentEnum;
import com.level3.admin.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSignupRequestDto {

    @NotBlank(message = "이름은 비어있을 수 없습니다")
    private String username;

    @Email(message = "형식에 맞게 입력하세요")
    @NotBlank(message = "이메일은 비어있을 수 없습니다")
    private String email;

    @NotBlank(message = "비밀번호는 비어있을 수 없습니다")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,15}$",
            message = "비밀번호는 8~15자리면서 알파벳, 숫자, 특수문자를 포함해야합니다")
    private String password;

    private DepartmentEnum department;

    @Builder.Default
    private boolean isAdmin = true;

    @Builder.Default
    private String adminToken = "";

    @Builder
    public User toEntity(){
        return User.builder()
                .username(username)
                .email(email)
                .password(password)
                .department(department)
                .build();
    }
}
