package com.sparta.learnspring.service;

import com.sparta.learnspring.dto.CommentRequestDto;
import com.sparta.learnspring.dto.CommentResponseDto;
import com.sparta.learnspring.entity.Comment;
import com.sparta.learnspring.entity.Post;
import com.sparta.learnspring.exception.RestApiException;
import com.sparta.learnspring.exception.custom.CommentNotFoundException;
import com.sparta.learnspring.jwt.JwtUtil;
import com.sparta.learnspring.repoistory.CommentRepository;
import com.sparta.learnspring.repoistory.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Locale;

@Slf4j(topic = "CommentService 로그")
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MessageSource messageSource;
    private final JwtUtil jwtUtil;

    public CommentResponseDto createComment(Long postId, CommentRequestDto commentRequestDto, Principal principal) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CommentNotFoundException(
                        messageSource.getMessage(
                                "not.found.comment",
                                null,
                                "Wrong Comment",
                                Locale.getDefault()
                        )
                ));

        Comment comment = new Comment(commentRequestDto, principal);
        comment.setPost(post);
        commentRepository.save(comment);

        post.updateComments(comment);
        postRepository.save(post);

        return new CommentResponseDto(comment);

    }

    @Transactional
    public List<CommentResponseDto> updateComment(Long id, CommentRequestDto commentRequestDto, Principal principal) {
        // 해당 댓글이 있는지 확인
        Comment comment = findComment(id);

        if (!comment.getUsername().equals(principal.getName())) {
            log.info("게시글 수정 실패.");
            throw new AccessDeniedException(
                    messageSource.getMessage(
                            "access.denied.update",
                            null,
                            Locale.getDefault()
                    )
            );
        }

        // 댓글 수정
        comment.update(commentRequestDto);
        log.info("댓글 수정 성공");
        return commentRepository.findById(id).stream().map(CommentResponseDto::new).toList();

    }

    public RestApiException deletePost(Long id, CommentRequestDto commentRequestDto, Principal principal) {
        // 해당 댓글이 존재하는지 확인
        Comment comment = findComment(id);

        // 사용자 확인
        if (!comment.getUsername().equals(principal.getName())) {
            log.info("댓글 삭제 실패");
            throw new AccessDeniedException(
                    messageSource.getMessage(
                            "access.denied.delete",
                            null,
                            Locale.getDefault()
                    )
            );
        }

        // 댓글 삭제
        commentRepository.delete(comment);
        log.info("댓글 삭제 성공");
        return new RestApiException("댓글 삭제 성공", HttpStatus.OK.value());

    }

    private Comment findComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new CommentNotFoundException(
                        messageSource.getMessage(
                                "not.found.comment",
                                null,
                                "Wrong Comment",
                                Locale.getDefault()
                        )
                )
        );
    }
}
