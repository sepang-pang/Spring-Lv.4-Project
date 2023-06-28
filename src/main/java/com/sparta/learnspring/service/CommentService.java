package com.sparta.learnspring.service;

import com.sparta.learnspring.dto.CommentRequestDto;
import com.sparta.learnspring.dto.CommentResponseDto;
import com.sparta.learnspring.dto.MsgDto;
import com.sparta.learnspring.entity.Comment;
import com.sparta.learnspring.entity.Post;
import com.sparta.learnspring.jwt.JwtUtil;
import com.sparta.learnspring.repoistory.CommentRepository;
import com.sparta.learnspring.repoistory.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    @Transactional
    public List<CommentResponseDto> updateComment(Long id, CommentRequestDto commentRequestDto, Principal principal) {
        // 해당 댓글이 있는
        Comment comment = findComment(id);

        // 사용자 확인
        if (comment.getUsername().equals(principal.getName())) {
            comment.update(commentRequestDto);
            log.info("댓글 수정 성공");
            return commentRepository.findById(id).stream().map(CommentResponseDto::new).toList();
        } else {
            log.info("게시글 수정 실패 : 작성자가 아닙니다.");
            return null;
        }
    }

    public MsgDto deletePost(Long id, CommentRequestDto commentRequestDto, Principal principal) {
        // 해당 게시글이 존재하는지 확인
        Comment comment = findComment(id);

        // 사용자 확인
        if (comment.getUsername().equals(principal.getName())) {
            // 게시글 삭제
            commentRepository.delete(comment);
            log.info("댓글 삭제 성공");
            return new MsgDto("댓글 삭제 성공", HttpStatus.OK.value());
        } else {
            log.info("댓글 삭제 실패");
            return new MsgDto("댓글 삭제 실패", HttpStatus.BAD_REQUEST.value());
        }
    }

    private Comment findComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );
    }

}
