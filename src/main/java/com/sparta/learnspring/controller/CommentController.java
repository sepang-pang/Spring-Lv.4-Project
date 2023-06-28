package com.sparta.learnspring.controller;

import com.sparta.learnspring.dto.CommentRequestDto;
import com.sparta.learnspring.dto.CommentResponseDto;
import com.sparta.learnspring.dto.MsgDto;
import com.sparta.learnspring.entity.UserRoleEnum;
import com.sparta.learnspring.service.CommentService;
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
    @PostMapping("/comments/{postId}")
    public CommentResponseDto commentResponseDto (@PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto, Principal principal) {
        return commentService.createComment(postId, commentRequestDto, principal);
    }

    // 게시슬 수정
    @PutMapping("/comments/{id}")
    public List<CommentResponseDto> UpdateDto(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, Principal principal) {
        return commentService.updateComment(id, commentRequestDto, principal);
    }

    // 게시글 삭제
    @DeleteMapping("/comments/{id}")
    public MsgDto msgDto(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, Principal principal) {
        return commentService.deletePost(id, commentRequestDto, principal);
    }
}
