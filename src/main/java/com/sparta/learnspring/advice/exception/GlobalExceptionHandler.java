package com.sparta.learnspring.advice.exception;

import com.sparta.learnspring.advice.custom.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<RestApiException> illegalArgumentExceptionHandler(IllegalArgumentException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<RestApiException> nullPointerExceptionHandler(NullPointerException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.NOT_FOUND
        );
    }

    // 댓글 Not Found
    @ExceptionHandler({CommentNotFoundException.class})
    public ResponseEntity<RestApiException> notFoundCommentExceptionHandler(CommentNotFoundException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.NOT_FOUND
        );
    }

    // 게시글 Not Found
    @ExceptionHandler({PostNotFoundException.class})
    public ResponseEntity<RestApiException> notFoundCommentExceptionHandler(PostNotFoundException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.NOT_FOUND
        );
    }

    // 좋아요 Not Found
    @ExceptionHandler({LikeNotFoundException.class})
    public ResponseEntity<RestApiException> notFoundLikeExceptionHandler(LikeNotFoundException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.NOT_FOUND
        );
    }

    // 본인이 아닐 때
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<RestApiException> accessDeniedExceptionHandler(AccessDeniedException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.FORBIDDEN
        );
    }

    // 중복 에러
    @ExceptionHandler({DuplicateException.class})
    public ResponseEntity<RestApiException> duplicateUserExceptionHandler(DuplicateException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }


    // 틀린 암호
    @ExceptionHandler({InvalidPasswordException.class})
    public ResponseEntity<RestApiException> invalidPasswordExceptionHandler(InvalidPasswordException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({IOException.class})
    public ResponseEntity<RestApiException> IOExceptionHandler(IOException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.UNAUTHORIZED
        );
    }

    // 본인 게시글, 댓글 좋아요 금지
    public ResponseEntity<RestApiException> SelfLikeNotAllowedExceptionHandler(SelfLikeNotAllowedException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }


}