package com.sparta.learnspring.advice.custom;

public class SelfLikeNotAllowedException extends RuntimeException{
    public SelfLikeNotAllowedException(String message) {
        super(message);
    }
}
