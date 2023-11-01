package com.level3.admin.entity;

import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity(name = "lecture")
public class Lecture {

    // TODO PK키 뭘로 할거?
    private String title; // 강의명
    private int price; // 강의가격
    private String lecInfo; // 강의정보
    private LocalDate regDate; // 등록일
}
