package com.sparta.learnspring.like.comment.like.service;

import com.sparta.learnspring.advice.custom.CommentNotFoundException;
import com.sparta.learnspring.advice.custom.LikeNotFoundException;
import com.sparta.learnspring.advice.custom.SelfLikeNotAllowedException;
import com.sparta.learnspring.comment.entity.Comment;
import com.sparta.learnspring.comment.repository.CommentRepository;
import com.sparta.learnspring.common.dto.ApiResponseDto;
import com.sparta.learnspring.like.comment.like.entity.CommentLike;
import com.sparta.learnspring.like.comment.like.repository.CommentLikeRepository;
import com.sparta.learnspring.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "Comment Like service log")
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    private final MessageSource messageSource;

    @Transactional
    public ResponseEntity<ApiResponseDto> insert(Long id, User user) {
        // 댓글이 있는지 확인
        Comment comment = findComment(id);

        // 본인 댓글 좋아요 금지
        if (comment.getUsername().equals(user.getUsername())) {
            throw new SelfLikeNotAllowedException(
                    messageSource.getMessage(
                            "self.not.allowed.comment",
                            null,
                            Locale.getDefault()
                    )
            );
        }

        // 좋아요 성공
        comment.countLike();

        // 관계 설정
        CommentLike commentLike = new CommentLike(user, comment);
        commentLikeRepository.save(commentLike);
        return ResponseEntity.ok().body(new ApiResponseDto(200L, "좋아요 성공"));

    }

    @Transactional
    public ResponseEntity<ApiResponseDto> delete(Long id, User user) {
        // 댓글이 있는지 확인
        Comment comment = findComment(id);

        CommentLike like = commentLikeRepository.findByUserAndComment(user, comment)
                .orElseThrow(() -> new LikeNotFoundException(
                                messageSource.getMessage(
                                        "not.found.like",
                                        null,
                                        Locale.getDefault()
                                )
                        )
                );

        comment.decreaseLike();
        commentLikeRepository.delete(like);
        return ResponseEntity.ok().body(new ApiResponseDto(200L, "좋아요 삭제 성공"));
    }

    private Comment findComment(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(
                                messageSource.getMessage(
                                        "not.found.comment",
                                        null,
                                        Locale.getDefault()
                                )
                        )
                );
    }


}
