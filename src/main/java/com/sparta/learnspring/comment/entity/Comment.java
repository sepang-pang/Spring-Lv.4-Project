package com.sparta.learnspring.comment.entity;

import com.sparta.learnspring.comment.dto.CommentRequestDto;
import com.sparta.learnspring.post.entity.Post;
import com.sparta.learnspring.user.entity.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.Principal;

@Entity
@Getter
@Setter
@Table(name = "comment")
@NoArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "contents", nullable = false)
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public Comment(CommentRequestDto commentRequestDto, Principal principal) {
        this.username = principal.getName();
        this.contents = commentRequestDto.getContents();
    }

    public void update (CommentRequestDto commentRequestDto) {
        this.contents = commentRequestDto.getContents();
    }

}
