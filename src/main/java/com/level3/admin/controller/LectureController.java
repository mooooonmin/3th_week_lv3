package com.level3.admin.controller;

import com.level3.admin.dto.lecture.LectureRequestDto;
import com.level3.admin.dto.lecture.LectureResponseDto;
import com.level3.admin.entity.LectureCategory;
import com.level3.admin.entity.Lecturer;
import com.level3.admin.repository.LecturerRepository;
import com.level3.admin.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;
    private final LecturerRepository lecturerRepository;

    // TODO 강의 등록
    @Secured({"ROLE_STAFF", "ROLE_MANAGER"})
    @PostMapping("/lecture")
    public ResponseEntity<LectureResponseDto> createLecture(@RequestBody LectureRequestDto requestDto,
                                                            @RequestParam String lecturerName) {
        Lecturer lecturer = lecturerRepository.findByName(lecturerName)
                .orElseThrow(() -> new IllegalArgumentException("해당 강사가 없습니다"));

        LectureResponseDto responseDto = lectureService.createLecture(requestDto, lecturer);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // TODO 선택 강의 조회
    @Secured({"ROLE_STAFF", "ROLE_MANAGER"})
    @GetMapping("/lecture/{id}")
    public ResponseEntity<LectureResponseDto> getLectureById(@PathVariable Long id) {
        LectureResponseDto lecture= lectureService.getLectureById(id);
        return new ResponseEntity<>(lecture, HttpStatus.OK);
    }

    // TODO 전체 강의 조회
    @Secured({"ROLE_STAFF", "ROLE_MANAGER"})
    @GetMapping("/lectures")
    public ResponseEntity<List<LectureResponseDto>> getAllLectures() {
        List<LectureResponseDto> lectures = lectureService.getAllLectures();
        return new ResponseEntity<>(lectures, HttpStatus.OK);
    }

    // TODO 강의 카테고리 조회
    @Secured({"ROLE_STAFF", "ROLE_MANAGER"})
    @GetMapping("/category")
    public ResponseEntity<List<LectureResponseDto>> getLecturesByCategory(@RequestParam LectureCategory category) {
        List<LectureResponseDto> responseDtos = lectureService.getLecturesByCategory(category);
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    // TODO 선택 강의 수정
    @Secured("ROLE_MANAGER")
    @PutMapping("/lecture/{id}")
    public ResponseEntity<?> updateLecture(@PathVariable Long id,
                                            @RequestBody LectureRequestDto requestDto) {
        try {
            lectureService.updateLecture(id, requestDto);
            return ResponseEntity.ok("강의 정보가 성공적으로 수정되었습니다");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("강의 정보 수정에 실패하였습니다");
        }
    }

    // TODO 강의 삭제
    @Secured("ROLE_MANAGER")
    @DeleteMapping("/lecture/{id}")
    public ResponseEntity<?> deleteLecture(@PathVariable Long id) {
        try {
            lectureService.deleteLecture(id);
            return ResponseEntity.ok().body("강의 정보가 삭제되었습니다");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("강의 정보 삭제 실패하였습니다");
        }
    }

}
