package com.level3.admin.entity;

import com.level3.admin.dto.LectureRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity(name = "lecture")
public class Lecture {

    @Id
    @Column(name = "title")
    private String title; // 강의명
    @Column(name = "price", nullable = false)
    private int price; // 강의가격
    @Column(name = "lecInfo")
    private String lecInfo; // 강의정보
    @Column(name = "reg_date")
    private LocalDate regDate; // 등록일

    public Lecture(LectureRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.price = requestDto.getPrice();
        this.lecInfo = requestDto.getLecInfo();
        this.regDate = LocalDate.now();
    }
}
