package com.sparta.learnspring.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RestApiException {
    private final String errorMessage;
    private final int statusCode;
}
