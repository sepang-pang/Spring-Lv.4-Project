package com.sparta.learnspring.Entity;

import com.sparta.learnspring.Dto.RequestDto;
import com.sparta.learnspring.Dto.ResponseDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "post")
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "contents", nullable = false)
    private String contents;
    @Column(name = "password", nullable = false)
    private Integer password;

    public Post(RequestDto requestDto) {
        this.name = requestDto.getName();
        this.contents = requestDto.getContents();
        this.password = requestDto.getPassword();
    }

    public Post(ResponseDto responseDto) {
        this.name = responseDto.getName();
        this.contents = responseDto.getContents();
    }

    public void update (RequestDto requestDto) {
        this.name = requestDto.getName();
        this.contents = requestDto.getContents();
    }



}