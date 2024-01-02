package com.level3.admin.domain.course.entity;

import com.level3.admin.domain.course.dto.CourseRequestDto;
import com.level3.admin.domain.instructor.entity.Instructor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseCategory category;

    @ManyToOne
    @JoinColumn(name = "instructor_id", nullable = false)
    private Instructor instructor;

    @Column(nullable = false)
    private LocalDate registrationDate;

    public Course(Instructor instructor, CourseRequestDto courseRequestDto, CourseCategory category) {
        this.title = courseRequestDto.getTitle();
        this.price = courseRequestDto.getPrice();
        this.description = courseRequestDto.getDescription();
        this.category = category;
        this.instructor = instructor;
        this.registrationDate = LocalDate.now();
    }


    public void updateCourseDetails(CourseRequestDto courseRequestDto) {
        if (courseRequestDto.getTitle() != null) {
            this.title = courseRequestDto.getTitle();
        }
        if (courseRequestDto.getPrice() >= 0) {
            this.price = courseRequestDto.getPrice();
        }
        if (courseRequestDto.getDescription() != null) {
            this.description = courseRequestDto.getDescription();
        }
        if (courseRequestDto.getCategory() != null) {
            this.category = courseRequestDto.getCategory();
        }
    }

}
