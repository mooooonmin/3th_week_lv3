package com.level3.admin.entity;

import com.level3.admin.dto.lecturer.LecturerRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "lecturer")
@Entity
@NoArgsConstructor
public class Lecturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "career", nullable = false)
    private String career;

    @Column(name = "company", nullable = false)
    private String company;

    @Column(name = "number", nullable = false)
    private String number;

    @Column(name = "info", nullable = false)
    private String info;

    public Lecturer(LecturerRequestDto requestDto) {
        this.name = requestDto.getName();
        this.career = requestDto.getCareer();
        this.company = requestDto.getCompany();
        this.number = requestDto.getNumber();
        this.info = requestDto.getInfo();
    }

    public void update(LecturerRequestDto requestDto) {
        this.name = requestDto.getName();
        this.career = requestDto.getCareer();
        this.company = requestDto.getCompany();
        this.number = requestDto.getNumber();
        this.info = requestDto.getInfo();
    }
}