package com.level3.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.level3.admin.dto.signup.UserSignupRequestDto;
import com.level3.admin.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("회원가입이 정상 동작 여부")
    public void signUpUserTest() throws Exception {
        // Given
        UserSignupRequestDto signUpRequest = new UserSignupRequestDto();
        signUpRequest.setUsername("testUser");
        signUpRequest.setPassword("testPassword");
        signUpRequest.setEmail("test@test.com");
        signUpRequest.setAdmin(true);
        signUpRequest.setAdminToken("asdfasdfasdfasdf");

        // When & Then
        mockMvc.perform(post("/api/user/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + "7Iqk7YyM66W07YOA7L2U65Sp7YG065+9U3ByaW5n6rCV7J2Y7Yqc7YSw7LWc7JuQ67mI7J6F64uI64ukLg")
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andDo(print())
                .andExpect(status().isOk());

    }
}
