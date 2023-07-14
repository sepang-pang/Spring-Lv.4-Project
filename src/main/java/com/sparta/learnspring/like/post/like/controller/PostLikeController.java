package com.sparta.learnspring.like.post.like.controller;

import com.sparta.learnspring.common.dto.ApiResponseDto;
import com.sparta.learnspring.common.security.UserDetailsImpl;
import com.sparta.learnspring.like.post.like.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j(topic = "Post Like Controller Log")
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/post/{postId}/like")
    public ResponseEntity<ApiResponseDto> insert(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postLikeService.insert(postId, userDetails.getUser());
    }

    @DeleteMapping("/post/{postId}/like")
    public ResponseEntity<ApiResponseDto> delete(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postLikeService.delete(postId, userDetails.getUser());
    }
}
