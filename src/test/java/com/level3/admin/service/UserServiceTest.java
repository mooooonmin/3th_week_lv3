package com.level3.admin.service;

import static org.junit.jupiter.api.Assertions.*;

import com.level3.admin.dto.signup.UserSignupRequestDto;
import com.level3.admin.entity.User;
import com.level3.admin.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        user.setAdmin(signUpRequest.isAdmin());

        when(userRepository.save(any(User.class))).thenReturn(user);

        // When
        User result = userService.signUp(signUpRequest);

        // Then
        assertNotNull(result);
        assertEquals(signUpRequest.getUsername(), result.getUsername());
        assertEquals(signUpRequest.getPassword(), result.getPassword());
        assertEquals(signUpRequest.getEmail(), result.getEmail());
        assertEquals(signUpRequest.isAdmin(), result.isAdmin());
    }
}
