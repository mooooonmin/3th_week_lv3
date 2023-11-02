package com.level3.admin.entity;

import com.level3.admin.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Table(name = "user")
@Entity
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "dept")
    @Enumerated(EnumType.STRING)
    private DepartmentEnum department;

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    public User(UserRequestDto requestDto) {
        this.email = requestDto.getEmail();
        this.password = requestDto.getPassword();
        this.department = requestDto.getDepartment();
        this.role = requestDto.getRole();

    }

}
