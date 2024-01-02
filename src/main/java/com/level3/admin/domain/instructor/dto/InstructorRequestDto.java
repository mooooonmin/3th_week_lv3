package com.level3.admin.domain.instructor.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InstructorRequestDto {

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @Min(value = 1, message = "경력은 1년 이상이어야 합니다.")
    private int experience;

    @NotBlank(message = "회사명을 입력해주세요.")
    private String company;

    @Pattern(regexp = "^\\+?[0-9][0-9]{7,14}$", message = "올바른 전화번호 형식이 아닙니다.")
    private String phoneNumber;

    @Size(max = 500, message = "소개는 500자 이내여야 합니다.")
    private String bio;

}
