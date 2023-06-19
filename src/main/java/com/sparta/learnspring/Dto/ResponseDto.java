package com.sparta.learnspring.Dto;

import com.sparta.learnspring.Entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResponseDto {
    private String name;
    private String contents;
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public ResponseDto(Post post) {
        this.name = post.getName();
        this.contents = post.getContents();
        this.id = post.getId();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }


}
