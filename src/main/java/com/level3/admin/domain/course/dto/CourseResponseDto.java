package com.level3.admin.domain.course.dto;

import com.level3.admin.domain.course.entity.Course;
import com.level3.admin.domain.course.entity.CourseCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class CourseResponseDto {

    private Long id;
    private String title;
    private Integer price;
    private String description;
    private CourseCategory category;
    private String instructorName;
    private LocalDate registrationDate;

    public CourseResponseDto(Course savedCourse, String instructorName) {
        this.id = savedCourse.getId();
        this.title = savedCourse.getTitle();
        this.price = savedCourse.getPrice();
        this.description = savedCourse.getDescription();
        this.category = savedCourse.getCategory();
        this.instructorName = instructorName;
        this.registrationDate = savedCourse.getRegistrationDate();
    }

    public CourseResponseDto(Course course) {
        this.id = course.getId();
        this.title = course.getTitle();
        this.price = course.getPrice();
        this.description = course.getDescription();
        this.category = course.getCategory();
        this.instructorName = course.getInstructor().getName();
        this.registrationDate = course.getRegistrationDate();
    }

}
