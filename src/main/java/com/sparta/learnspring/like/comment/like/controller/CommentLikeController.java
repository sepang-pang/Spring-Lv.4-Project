package com.sparta.learnspring.like.comment.like.controller;

import com.sparta.learnspring.common.dto.ApiResponseDto;
import com.sparta.learnspring.common.security.UserDetailsImpl;
import com.sparta.learnspring.like.comment.like.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j(topic = "Comment Like Controller Log")
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping("/comment/{commentId}/like")
    public ResponseEntity<ApiResponseDto> insert(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentLikeService.insert(commentId, userDetails.getUser());
    }

    @DeleteMapping("/comment/{commentId}/dislike")
    public ResponseEntity<ApiResponseDto> delete(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentLikeService.delete(commentId, userDetails.getUser());
    }
}
