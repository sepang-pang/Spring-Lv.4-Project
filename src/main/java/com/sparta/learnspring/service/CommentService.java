package com.sparta.learnspring.service;

import com.sparta.learnspring.dto.CommentRequestDto;
import com.sparta.learnspring.dto.CommentResponseDto;
import com.sparta.learnspring.entity.Comment;
import com.sparta.learnspring.entity.Post;
import com.sparta.learnspring.jwt.JwtUtil;
import com.sparta.learnspring.repoistory.CommentRepository;
import com.sparta.learnspring.repoistory.PostRepository;
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
    private final PostRepository postRepository;
    private final JwtUtil jwtUtil;
    public CommentResponseDto createComment(Long postId, CommentRequestDto commentRequestDto, Principal principal) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        Comment comment = new Comment(commentRequestDto, principal);
        comment.setPost(post);
        commentRepository.save(comment);

        post.updateComments(comment);
        postRepository.save(post);

        return new CommentResponseDto(comment);

    }

    public List<CommentResponseDto> displayComments() {
        log.info("전체 댓글 조회");
        return commentRepository.findAllByOrderByModifiedAtDesc().stream().map(CommentResponseDto::new).toList();
    }
}
