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
    private String name; // 강사명

    @Column(name = "career", nullable = false)
    private String career; // 경력(년차)

    @Column(name = "company", nullable = false)
    private String company; // 회사

    @Column(name = "number", nullable = false)
    private String number; // 전화번호

    @Column(name = "info", nullable = false)
    private String info; // 강사소개

    // 강의 - 강사 단방향 설정을 위해 일단 여기서는 삭제
//    @OneToMany(mappedBy = "lecturer") // 강사의 해당강의를 조회할 수 있게 해줌
//    private List<Lecture> lectures = new ArrayList<>();

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