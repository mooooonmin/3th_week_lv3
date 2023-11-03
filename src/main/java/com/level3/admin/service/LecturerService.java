package com.level3.admin.service;

import com.level3.admin.dto.lecturer.LecturerRequestDto;
import com.level3.admin.dto.lecturer.LecturerResponseDto;
import com.level3.admin.entity.Lecturer;
import com.level3.admin.entity.UserRoleEnum;
import com.level3.admin.jwt.JwtUtil;
import com.level3.admin.repository.LecturerRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class LecturerService {

    private final LecturerRepository lecturerRepository;
    private JwtUtil jwtUtil;

    @Autowired
    public LecturerService(LecturerRepository lecturerRepository, JwtUtil jwtUtil) {
        this.lecturerRepository = lecturerRepository;
        this.jwtUtil = jwtUtil;

    }

    // 강사 등록 (매니저와 스태프 모두 가능)
    public LecturerResponseDto createLecturer(HttpServletRequest request, LecturerRequestDto requestDto) {
        // 토큰에서 사용자의 권한을 가져옴
        String token = jwtUtil.getTokenFromRequest(request);
        UserRoleEnum role = jwtUtil.getUserRoleFromToken(token);

        // 사용자의 권한이 매니저 또는 스태프인지 확인
        if (role.equals(UserRoleEnum.MANAGER) || role.equals(UserRoleEnum.STAFF)) {
            Lecturer lecturer = new Lecturer(requestDto);
            lecturerRepository.save(lecturer);
            return new LecturerResponseDto(lecturer);
        } else {
            throw new RuntimeException("매니저 또는 스태프만 강사를 등록할 수 있습니다");
        }
    }

    // 매니저 권한만 강사 수정 가능
    @Transactional
    public void updateLecturer(HttpServletRequest request, Long id, LecturerRequestDto requestDto) {
        // 토큰에서 사용자의 권한을 가져옴
        String token = jwtUtil.getTokenFromRequest(request);
        UserRoleEnum role = jwtUtil.getUserRoleFromToken(token);

        // 사용자의 권한이 매니저인지 확인
        if (role.equals(UserRoleEnum.MANAGER)) {
            // 강사 정보 찾고 수정 - 강사 아이디로 찾기
            Lecturer lecturer = lecturerRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("해당 강사 정보를 찾을 수 없습니다"));

            lecturer.update(requestDto);
            lecturerRepository.save(lecturer);
        } else {
            throw new RuntimeException("매니저만 강사 정보 수정이 가능합니다");
        }
    }
}