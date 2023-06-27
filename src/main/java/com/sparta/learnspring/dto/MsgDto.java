package com.sparta.learnspring.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class MsgDto {
    private final String msg;
    private final int statusCode;
}
