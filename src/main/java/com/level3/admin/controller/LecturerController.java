package com.level3.admin.controller;

import com.level3.admin.dto.lecturer.LecturerRequestDto;
import com.level3.admin.dto.lecturer.LecturerResponseDto;
import com.level3.admin.service.LecturerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/login")
public class LecturerController {

    private final LecturerService lecturerService;

    @Autowired
    public LecturerController(LecturerService lecturerService) {
        this.lecturerService = lecturerService;
    }

    // @/api/user/login/lecturer - 스태프와 매니저의 권한 확인후 강사 등록
    @PostMapping("/lecturer")
    public ResponseEntity<LecturerResponseDto> createLecturer(HttpServletRequest request, @RequestBody LecturerRequestDto requestDto) {
        LecturerResponseDto responseDto = lecturerService.createLecturer(request, requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }


    // @/api/user/login/lecturer/{id} - 선택한 강사 정보 수정 - 매니저 권한만 가능
    @PutMapping("/lecturer/{id}")
    public ResponseEntity<?> updateLecturer(HttpServletRequest request,
                                            @PathVariable Long id,
                                            @RequestBody LecturerRequestDto requestDto) {
        try {
            lecturerService.updateLecturer(request, id, requestDto);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
