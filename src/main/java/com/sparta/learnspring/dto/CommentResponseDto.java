package com.sparta.learnspring.dto;

import com.sparta.learnspring.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponseDto {
        private final String username;
        private final String contents;
        private final Long id;
        private final LocalDateTime createdAt;
        private final LocalDateTime modifiedAt;

        public CommentResponseDto(Comment comment) {
            this.username = comment.getUsername();
            this.contents = comment.getContents();
            this.id = comment.getId();
            this.createdAt = comment.getCreatedAt();
            this.modifiedAt = comment.getModifiedAt();
        }


}
