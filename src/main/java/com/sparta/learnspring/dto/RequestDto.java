package com.sparta.learnspring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDto {
    private String username;
    private String contents;
    private Integer password;
}
