package com.level3.admin.domain.instructor.dto;

import com.level3.admin.domain.instructor.entity.Instructor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InstructorResponseDto {

    private Long id;
    private String name;
    private int experience;
    private String company;
    private String phoneNumber;
    private String bio;

    public InstructorResponseDto(Instructor saveInstructor) {
        this.id = saveInstructor.getId();
        this.name = saveInstructor.getName();
        this.experience = saveInstructor.getExperience();
        this.company = saveInstructor.getCompany();
        this.phoneNumber = saveInstructor.getPhoneNumber();
        this.bio = saveInstructor.getBio();
    }

}
