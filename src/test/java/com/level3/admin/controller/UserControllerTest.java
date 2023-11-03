package com.level3.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.level3.admin.dto.login.UserLoginRequestDto;
import com.level3.admin.dto.login.UserLoginResponseDto;
import com.level3.admin.dto.signup.UserSignupRequestDto;
import com.level3.admin.dto.signup.UserSignupResponseDto;
import com.level3.admin.security.UserDetailsServiceImpl;
import com.level3.admin.service.UserService;
import com.level3.admin.entity.UserRoleEnum;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.level3.admin.entity.DepartmentEnum.DEVELOPMENT;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    public void testJoin() throws Exception {

        // Given
        UserSignupRequestDto requestDto = new UserSignupRequestDto();
        requestDto.setUsername("testUser");
        requestDto.setPassword("testPassword");
        requestDto.setEmail("test@test.com");
        requestDto.setAdmin(true);
        requestDto.setAdminToken("asdfasdfasdfasdf");
        requestDto.setDepartment(DEVELOPMENT);

        UserSignupResponseDto responseDto = new UserSignupResponseDto("testUser", "test@test.com", UserRoleEnum.MANAGER, DEVELOPMENT, "성공적으로 가입되셨습니다");

        when(userService.signUp(any(UserSignupRequestDto.class))).thenReturn(responseDto);

        // When & Then
        mockMvc.perform(post("/api/user/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(responseDto)));

        verify(userService, times(1)).signUp(any(UserSignupRequestDto.class));
    }
}
