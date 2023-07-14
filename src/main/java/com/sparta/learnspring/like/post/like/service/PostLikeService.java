package com.sparta.learnspring.like.post.like.service;

import com.sparta.learnspring.advice.custom.DuplicateException;
import com.sparta.learnspring.advice.custom.LikeNotFoundException;
import com.sparta.learnspring.advice.custom.PostNotFoundException;
import com.sparta.learnspring.advice.custom.SelfLikeNotAllowedException;
import com.sparta.learnspring.common.dto.ApiResponseDto;
import com.sparta.learnspring.like.post.like.entity.PostLike;
import com.sparta.learnspring.like.post.like.repository.PostLikeRepository;
import com.sparta.learnspring.post.entity.Post;
import com.sparta.learnspring.post.repository.PostRepository;
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
@Slf4j(topic = "PostLikeService Log")
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final MessageSource messageSource;

    // 좋아요 삽입
    @Transactional
    public ResponseEntity<ApiResponseDto> insert(Long id, User user) {
        // 해당 게시글이 존재하는지 확인
        Post post = findPost(id);

        // 본인 게시글 좋아요 금지
        if (post.getUsername().equals(user.getUsername())) {
            throw new SelfLikeNotAllowedException(
                    messageSource.getMessage(
                            "self.not.allowed",
                            null,
                            Locale.getDefault()
                    )
            );
        }

        // 본인이 이미 좋아요를 했을시 예외 발생
        if (postLikeRepository.findByUserAndPost(user, post).isPresent()) {
            throw new DuplicateException(
                    messageSource.getMessage(
                            "duplicate.like",
                            null,
                            Locale.getDefault()
                    )
            );
        }

        // 좋아요 성공
        post.countLike();

        // 관계 설정
        PostLike postLike = new PostLike(user, post);
        postLikeRepository.save(postLike);
        return ResponseEntity.ok().body(new ApiResponseDto(200L, "좋아요 성공"));
    }

    // 좋아요 삭제
    @Transactional
    public ResponseEntity<ApiResponseDto> delete(Long id, User user) {
        // 해당 게시글이 존재하는지 확인
        Post post = findPost(id);

        PostLike like = postLikeRepository.findByUserAndPost(user, post)
                .orElseThrow(() -> new LikeNotFoundException(
                                messageSource.getMessage(
                                        "not.found.like",
                                        null,
                                        Locale.getDefault()
                                )
                        )
                );
        post.discount();
        postLikeRepository.delete(like);
        return ResponseEntity.ok().body(new ApiResponseDto(200L, "좋아요 삭제 성공"));
    }


    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new PostNotFoundException(
                        messageSource.getMessage(
                                "not.found.post",
                                null,
                                Locale.getDefault()
                        )
                )
        );
    }
}
