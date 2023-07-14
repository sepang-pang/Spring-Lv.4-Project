package com.sparta.learnspring.post.entity;

import com.sparta.learnspring.comment.entity.Comment;
import com.sparta.learnspring.post.dto.PostRequestDto;
import com.sparta.learnspring.user.entity.Timestamped;
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
public class Post extends Timestamped {
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