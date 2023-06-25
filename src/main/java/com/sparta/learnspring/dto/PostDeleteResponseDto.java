package com.sparta.learnspring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDeleteResponseDto {
    private Boolean success;

    public PostDeleteResponseDto(boolean success) {
        this.success = success;
    }
}
