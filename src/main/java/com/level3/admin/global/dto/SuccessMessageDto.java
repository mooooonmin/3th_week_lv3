package com.level3.admin.global.dto;

import lombok.Getter;

@Getter
public class SuccessMessageDto {
    private String message;
    public SuccessMessageDto(String message) {
        this.message = message;
    }
}