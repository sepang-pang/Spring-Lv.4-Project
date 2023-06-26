package com.sparta.learnspring.entity;

import com.sparta.learnspring.dto.RequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.Principal;

@Entity
@Setter
@Getter
@Table(name = "post")
@NoArgsConstructor
public class Post extends com.sparta.learnspring.entity.Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "contents", nullable = false)
    private String contents;
    @Column(name = "password", nullable = false)
    private Integer password;


    public Post(RequestDto requestDto, Principal principal) {
        this.username = principal.getName();
        this.contents = requestDto.getContents();
        this.password = requestDto.getPassword();
    }

    public void update (RequestDto requestDto) {
        this.contents = requestDto.getContents();
    }



}