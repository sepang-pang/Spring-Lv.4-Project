package com.sparta.learnspring.comment.controller;

import com.sparta.learnspring.comment.dto.CommentRequestDto;
import com.sparta.learnspring.comment.dto.CommentResponseDto;
import com.sparta.learnspring.advice.exception.RestApiException;
import com.sparta.learnspring.user.entity.UserRoleEnum;
import com.sparta.learnspring.comment.service.CommentService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;
    public CommentController (CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 작성
    @Secured(UserRoleEnum.Authority.USER)
    @PostMapping("/comments/{postId}") // 최종적으로 어떤 정보가 올지 명시해주는 게 restfull
    public CommentResponseDto commentResponseDto (@PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto, Principal principal) {
        return commentService.createComment(postId, commentRequestDto, principal);
    }

    // 댓글 수정
    @PutMapping("/comments/{id}")
    public List<CommentResponseDto> UpdateDto(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, Principal principal) {
        return commentService.updateComment(id, commentRequestDto, principal);
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{id}")
    public RestApiException msgDto(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, Principal principal) {
        return commentService.deletePost(id, commentRequestDto, principal);
    }
}
