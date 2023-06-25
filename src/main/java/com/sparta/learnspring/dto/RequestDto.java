package com.sparta.learnspring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDto {
    private String name = "임남명";
    private String contents = "안뇽하세요";
    private Integer password = 1234;
}
