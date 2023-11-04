package com.level3.admin.controller;

import com.level3.admin.dto.lecturer.LecturerRequestDto;
import com.level3.admin.dto.lecturer.LecturerResponseDto;
import com.level3.admin.service.LecturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LecturerController {

    private final LecturerService lecturerService;

    // @/api/lecturer - 스태프와 매니저의 권한 확인후 강사 등록
    @Secured({"ROLE_STAFF", "ROLE_MANAGER"})
    @PostMapping("/lecturer")
    public ResponseEntity<LecturerResponseDto> createLecturer(@RequestBody LecturerRequestDto requestDto) {

        System.out.println("Create Lecturer API called. Request Data: " + requestDto);

        LecturerResponseDto responseDto = lecturerService.createLecturer(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 선택한 강사 정보 조회
    @Secured({"ROLE_STAFF", "ROLE_MANAGER"})
    @GetMapping("/lecturer/{id}")
    public ResponseEntity<LecturerResponseDto> getLecturerById(@PathVariable Long id) {
        LecturerResponseDto lecturer = lecturerService.getLecturerById(id);
        return new ResponseEntity<>(lecturer, HttpStatus.OK);
    }

    // 전체 강사 정보 조회
    @Secured({"ROLE_STAFF", "ROLE_MANAGER"})
    @GetMapping("/lecturers")
    public ResponseEntity<List<LecturerResponseDto>> getAllLecturers() {
        List<LecturerResponseDto> lecturers = lecturerService.getAllLecturers();
        return new ResponseEntity<>(lecturers, HttpStatus.OK);
    }

    // @/api/lecturer/{id} - 선택한 강사 정보 수정 - 매니저 권한만 가능
    @Secured("ROLE_MANAGER")
    @PutMapping("/lecturer/{id}")
    public ResponseEntity<?> updateLecturer(@PathVariable Long id,
                                            @RequestBody LecturerRequestDto requestDto) {
        try {
            lecturerService.updateLecturer(id, requestDto);
            return ResponseEntity.ok("강사 정보가 성공적으로 수정되었습니다");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 선택한 강사 정보 삭제 - 매니저 권한만 가능
    @Secured("ROLE_MANAGER")
    @DeleteMapping("/lecturer/{id}")
    public ResponseEntity<?> deleteLecturer(@PathVariable Long id) {
        try {
            lecturerService.deleteLecturer(id);
            return ResponseEntity.ok().body("강사 정보가 삭제되었습니다.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
