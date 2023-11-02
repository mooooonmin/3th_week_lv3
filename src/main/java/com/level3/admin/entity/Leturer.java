package com.level3.admin.entity;

import com.level3.admin.dto.LeturerRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Table(name = "Lecturer")
@Entity
@NoArgsConstructor
public class Leturer {

    @Id
    @Column(name = "name")
    private String name; // 강사명
    @Column(name = "career", nullable = false)
    private String career; // 경력(년차)
    @Column(name = "company", nullable = false)
    private String company; // 회사
    @Column(name = "number", nullable = false)
    private String number; // 전화번호
    @Column(name = "info", nullable = false)
    private String info; // 강사소개

    public Leturer(LeturerRequestDto requestDto) {
        this.name = requestDto.getName();
        this.career = requestDto.getCareer();
        this.company = requestDto.getCompany();
        this.number = requestDto.getNumber();
        this.info = requestDto.getInfo();
    }
}