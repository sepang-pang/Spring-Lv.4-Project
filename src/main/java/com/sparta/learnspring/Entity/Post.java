package com.sparta.learnspring.Entity;

import com.sparta.learnspring.Dto.RequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Post {
    private Long id;
    private String name;
    private String contents;
    private Integer password;

    public Post(RequestDto requestDto) {
        this.name = requestDto.getName();
        this.contents = requestDto.getContents();
        this.password = requestDto.getPassword();
    }

    public void update(RequestDto requestDto) {
        setName(requestDto.getName());
        setContents(requestDto.getContents());
    }
}
