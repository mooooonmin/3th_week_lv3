package com.level3.admin.entity;

import jakarta.persistence.Entity;

@Entity(name = "lecturer")
public class Leturer {

    // TODO PK키 뭘로 할거?
    private String name; // 강사명
    private String career; // 경력(년차)
    private String company; // 회사
    private String number; // 전화번호
    private String info; // 강사소개
}