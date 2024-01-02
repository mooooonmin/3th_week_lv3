package com.level3.admin.domain.instructor.controller;

import com.level3.admin.domain.instructor.dto.InstructorRequestDto;
import com.level3.admin.domain.instructor.dto.InstructorResponseDto;
import com.level3.admin.domain.instructor.service.InstructorService;
import com.level3.admin.global.dto.SuccessMessageDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/instructors")
public class InstructorController {

    private final InstructorService instructorService;

    @PostMapping("/register")
    @PreAuthorize("hasAnyRole('MANAGER', 'STAFF')")
    public InstructorResponseDto registerInstructor(@Valid @RequestBody InstructorRequestDto instructorRequestDto) {
        return instructorService.registerInstructor(instructorRequestDto);
    }

    @GetMapping("/{instructorId}")
    @PreAuthorize("hasAnyRole('MANAGER', 'STAFF')")
    public InstructorResponseDto getInstructor(@PathVariable Long instructorId) {
        return instructorService.getInstructorDetails(instructorId);
    }

    @PatchMapping("/{instructorId}/update")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public InstructorResponseDto reviseInstructorDetails(@PathVariable Long instructorId, @Valid @RequestBody InstructorRequestDto instructorRequestDto) {
        return instructorService.reviseInstructorDetails(instructorId, instructorRequestDto);
    }

    @DeleteMapping("/{instructorId}")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public SuccessMessageDto deleteInstructor(@PathVariable Long instructorId) {
        instructorService.deleteInstructor(instructorId);
        return new SuccessMessageDto("해당 강사와 강의들이 삭제되었습니다");
    }

}
