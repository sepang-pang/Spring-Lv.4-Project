package com.sparta.learnspring.Dto;

import com.sparta.learnspring.Entity.Post;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDto {
    private String name;
    private String contents;
    private Long id;

    public ResponseDto(Post post) {
        this.name = post.getName();
        this.contents = post.getContents();
        this.id = post.getId();
    }

    public ResponseDto(Long id, String name, String contents) {
        this.id = id;
        this.name = name;
        this.contents = contents;
    }
}
