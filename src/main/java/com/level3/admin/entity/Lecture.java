package com.level3.admin.entity;

import com.level3.admin.dto.lecture.LectureRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Getter
@Setter
@Table(name = "lecture")
@Entity
@NoArgsConstructor
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lectureId;

    @Column(name = "title", nullable = false, unique = true)
    private String title; // 강의명

    @Column(name = "price", nullable = false)
    private int price; // 강의가격

    @Column(name = "lecInfo")
    private String lecInfo; // 강의정보

    @Column(name = "reg_date", updatable = false)
    @CreationTimestamp
    private LocalDate regDate; // 등록일

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private LectureCategory category;

    @ManyToOne // 강의(N) : 강사(1) = 한명의 강사는 다수의 강의 등록 가능
    @JoinColumn(name = "lecturer_name", nullable = false)
    private Lecturer lecturer;

    public Lecture(LectureRequestDto requestDto, Lecturer lecturer) {
        this.title = requestDto.getTitle();
        this.price = requestDto.getPrice();
        this.lecInfo = requestDto.getLecInfo();
        this.regDate = LocalDate.now();
        this.category = requestDto.getCategory();
        this.lecturer = lecturer;
    }
}
