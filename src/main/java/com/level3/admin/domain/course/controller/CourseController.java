package com.level3.admin.domain.course.controller;

import com.level3.admin.domain.course.dto.CourseRequestDto;
import com.level3.admin.domain.course.dto.CourseResponseDto;
import com.level3.admin.domain.course.entity.CourseCategory;
import com.level3.admin.domain.course.service.CourseService;
import com.level3.admin.global.dto.SuccessMessageDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    @PostMapping("/register")
    @PreAuthorize("hasAnyRole('MANAGER', 'STAFF')")
    public CourseResponseDto registerCourse(@RequestBody CourseRequestDto courseRequestDto) {
        return courseService.registerCourse(courseRequestDto);
    }

    @GetMapping("/{courseId}")
    @PreAuthorize("hasAnyRole('MANAGER', 'STAFF')")
    public CourseResponseDto getCourse(@PathVariable Long courseId) {
        return courseService.getCourseDetails(courseId);
    }

    @GetMapping("/{instructorId}/allCourses")
    @PreAuthorize("hasAnyRole('MANAGER', 'STAFF')")
    public List<CourseResponseDto> getCoursesByInstructor(@PathVariable Long instructorId) {
        return courseService.getCoursesFromSelectedInstructor(instructorId);
    }

    @GetMapping("/category")
    @PreAuthorize("hasAnyRole('MANAGER', 'STAFF')")
    public List<CourseResponseDto> getCoursesByCategory(@RequestParam("category") CourseCategory category) {
        return courseService.getCoursesByCategory(category);
    }

    @PatchMapping("/{courseId}/update")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public CourseResponseDto reviseCourseDetails(@PathVariable Long courseId, @Valid @RequestBody CourseRequestDto courseRequestDto) {
        return courseService.reviseCourseDetails(courseId, courseRequestDto);
    }

    @DeleteMapping("/{courseId}")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public SuccessMessageDto deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return new SuccessMessageDto("해당 강의가 삭제되었습니다.");
    }
}
