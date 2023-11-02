package com.level3.admin.service;

import com.level3.admin.dto.UserRequestDto;

import java.util.Map;

public interface UserService {

    // 회원가입
    // UserRequestDto -> 사용자로부터 회원가입에 필요한 정보를 받아옴
    // 반환값 Long -> 일반적으로 회원가입이 성공적으로 완료된 후, 생성된 사용자의 고유id 를 반환하는 것
    // 호출한 곳으로 예외를 전달 -> 예) 이미 존재하는 이메일로 회원가입 시도할 때
    public Long signUp(UserRequestDto requestDto) throws Exception;

    public String login(Map<String, String> users);
}
