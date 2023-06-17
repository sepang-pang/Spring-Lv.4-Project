package com.sparta.learnspring.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BooleanDto {
    private Boolean success;

    public BooleanDto(boolean success) {
        this.success = success;
    }
}
