package com.level3.admin.dto.lecture;


import com.level3.admin.entity.Lecture;
import com.level3.admin.entity.LectureCategory;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LectureResponseDto {

    private Long lectureId;
    private String title;
    private int price;
    private String lecInfo;
    private LectureCategory category;
    private String lecturerName;

    public LectureResponseDto(Lecture lecture) {
        this.lectureId = lecture.getLectureId();
        this.title = lecture.getTitle();
        this.price = lecture.getPrice();
        this.lecInfo = lecture.getLecInfo();
        this.category = lecture.getCategory();
        this.lecturerName = lecture.getLecturer().getName();
    }
}
