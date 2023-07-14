package com.sparta.learnspring.post.dto;

import com.sparta.learnspring.comment.dto.CommentResponseDto;
import com.sparta.learnspring.post.entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PostResponseDto {
    private final String username;
    private final String title;
    private final String contents;
    private final Long id;
    private List<CommentResponseDto> commentList;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public PostResponseDto(Post post) {
        this.username = post.getUsername();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.id = post.getId();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.commentList = post.getCommentList().stream()
                .map(CommentResponseDto::new)
                .toList();
    }

}
