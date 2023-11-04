package com.level3.admin.dto.lecture;

import com.level3.admin.entity.DepartmentEnum;
import com.level3.admin.entity.LectureCategory;
import com.level3.admin.entity.Lecturer;
import com.level3.admin.entity.UserRoleEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LectureRequestDto {
    private String title;
    private int price;
    private String lecInfo;
    private LectureCategory category;
    private String lecturerName;
}
