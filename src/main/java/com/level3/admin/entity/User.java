package com.level3.admin.entity;

import com.level3.admin.dto.UserSignupRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@Table(name = "user")
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public User(UserSignupRequestDto requestDto) {
        this.email = requestDto.getEmail();
        this.password = requestDto.getPassword();
        this.department = requestDto.getDepartment();
        this.role = requestDto.getRole();
    }

    // TODO 꼭 여기다 만들어줘야하나?
    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }

    // 비밀번호 일치 여부 확인 메서드
    public boolean checkPassword(PasswordEncoder passwordEncoder, String rawPassword) {
        return passwordEncoder.matches(rawPassword, this.password);
    }

    // 사용자 권한 부여 메서드
    public void addUserAuthority(UserRoleEnum role) {
        this.role = role;
    }


}
