package com.sparta.learnspring.service;

import com.sparta.learnspring.dto.CommentRequestDto;
import com.sparta.learnspring.dto.CommentResponseDto;
import com.sparta.learnspring.entity.Comment;
import com.sparta.learnspring.jwt.JwtUtil;
import com.sparta.learnspring.repoistory.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Slf4j(topic = "CommentService 로그")
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, Principal principal) {
        // 엔티티 화
        Comment comment = new Comment(commentRequestDto, principal);

        // DB 저장
        commentRepository.save(comment);

        // response 로 저장
        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
        log.info("댓글 작성 성공");
        return commentResponseDto;

    }

    public List<CommentResponseDto> displayComments() {
        log.info("전체 댓글 조회");
        return commentRepository.findAllByOrderByModifiedAtDesc().stream().map(CommentResponseDto::new).toList();
    }
}
