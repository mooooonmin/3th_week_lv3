package com.level3.admin.entity;

import com.level3.admin.dto.signup.UserSignupRequestDto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter // TODO 나중에 삭제
@Builder
@Table(name = "user")
// @EqualsAndHashCode(of = "userId") // 중복 회원을 방지하기 위해 - 근데 추천하지 않는?
@Entity
@AllArgsConstructor
@NoArgsConstructor // (access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "dept", nullable = false)
    @Enumerated(EnumType.STRING)
    private DepartmentEnum department;

    @Column(name = "user_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    @Column(name = "admin", nullable = false)  // admin 필드 추가
    private boolean admin;

    public User(UserSignupRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.email = requestDto.getEmail();
        this.password = requestDto.getPassword();
        this.department = requestDto.getDepartment();
        this.admin = requestDto.isAdmin();  // admin 필드 설정
    }


}
