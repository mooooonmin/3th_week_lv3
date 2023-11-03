package com.level3.admin.service;

import com.level3.admin.dto.signup.UserSignupRequestDto;
import com.level3.admin.dto.signup.UserSignupResponseDto;
import com.level3.admin.entity.User;
import com.level3.admin.entity.UserRoleEnum;
import com.level3.admin.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("회원가입 서비스 테스트")
    public void signUpUserTest() {
        // Given
        UserSignupRequestDto signUpRequest = new UserSignupRequestDto();
        signUpRequest.setUsername("testUser");
        signUpRequest.setPassword("testPassword");
        signUpRequest.setEmail("test@test.com");
        signUpRequest.setAdmin(true);
        signUpRequest.setAdminToken("asdfasdfasdfasdf");

        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(signUpRequest.getPassword());
        user.setEmail(signUpRequest.getEmail());
        user.setRole(UserRoleEnum.MANAGER);  // Role 설정

        when(userRepository.save(any(User.class))).thenReturn(user);

        // When
        UserSignupResponseDto result = userService.signUp(signUpRequest);

        // Then
        assertNotNull(result);
        assertEquals(signUpRequest.getUsername(), result.getUsername());
        assertEquals(signUpRequest.getEmail(), result.getEmail());
        assertEquals("성공적으로 가입되셨습니다.", result.getMessage());
        assertEquals(UserRoleEnum.MANAGER, result.getRole()); // Role 검증 추가
    }

    @Test
    @DisplayName("회원가입 서비스 테스트 - 잘못된 관리자 토큰")
    public void signUpUserWithInvalidAdminTokenTest() {
        // Given
        UserSignupRequestDto signUpRequest = new UserSignupRequestDto();
        signUpRequest.setUsername("testUser");
        signUpRequest.setPassword("testPassword");
        signUpRequest.setEmail("test@test.com");
        signUpRequest.setAdmin(true);
        signUpRequest.setAdminToken("wrongToken");

        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(signUpRequest.getPassword());
        user.setEmail(signUpRequest.getEmail());
        user.setRole(UserRoleEnum.STAFF);  // Role 설정

        when(userRepository.save(any(User.class))).thenReturn(user);

        // When
        UserSignupResponseDto result = userService.signUp(signUpRequest);

        // Then
        assertNotNull(result);
        assertEquals(signUpRequest.getUsername(), result.getUsername());
        assertEquals(signUpRequest.getEmail(), result.getEmail());
        assertEquals("토큰이 틀려서 STAFF로 가입됩니다.", result.getMessage());
        assertEquals(UserRoleEnum.STAFF, result.getRole()); // Role 검증 추가
    }
}
