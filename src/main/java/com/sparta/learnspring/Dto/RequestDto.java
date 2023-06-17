package com.sparta.learnspring.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDto {
    private String name;
    private String contents;
    private Integer password;
}
