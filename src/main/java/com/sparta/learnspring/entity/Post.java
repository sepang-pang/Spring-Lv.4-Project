package com.sparta.learnspring.entity;

import com.sparta.learnspring.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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
    @Column (name = "title", nullable = false)
    private String title;
    @Column(name = "contents", nullable = false)
    private String contents;

    @OneToMany (mappedBy = "post")
    private List<Comment> commentList = new ArrayList<>();

    public void updateComments (final Comment comment) {
        commentList.add(comment);
    }

    public Post(PostRequestDto postRequestDto, Principal principal) {
        this.username = principal.getName();
        this.title = postRequestDto.getTitle();
        this.contents = postRequestDto.getContents();
    }

    public void update (PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.contents = postRequestDto.getContents();
    }
}