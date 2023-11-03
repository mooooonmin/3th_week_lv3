package com.level3.admin.dto.lecturer;

import com.level3.admin.entity.Lecturer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LecturerResponseDto {
    private Long id;
    private String name;
    private String career;
    private String company;
    private String number;
    private String info;

    public LecturerResponseDto(Lecturer lecturer) {
        this.id = lecturer.getId();
        this.name = lecturer.getName();
        this.career = lecturer.getCareer();
        this.company = lecturer.getCompany();
        this.number = lecturer.getNumber();
        this.info = lecturer.getInfo();
    }
}
