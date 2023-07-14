package com.sparta.learnspring.advice.custom;

public class LikeNotFoundException extends RuntimeException{
    public LikeNotFoundException(String message) {
        super(message);
    }
}
