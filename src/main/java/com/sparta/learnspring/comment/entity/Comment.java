package com.sparta.learnspring.comment.entity;

import com.sparta.learnspring.comment.dto.CommentRequestDto;
import com.sparta.learnspring.like.comment.like.entity.CommentLike;
import com.sparta.learnspring.post.entity.Post;
import com.sparta.learnspring.user.entity.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@DynamicInsert
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

    @ColumnDefault("0")
    @Column(name = "likes", nullable = false)
    private Integer likes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @OneToMany(mappedBy = "comment", orphanRemoval = true)
    private List<CommentLike> commentLikeList = new ArrayList<>();

    public Comment(CommentRequestDto commentRequestDto, Principal principal) {
        this.username = principal.getName();
        this.contents = commentRequestDto.getContents();
    }

    public void update (CommentRequestDto commentRequestDto) {
        this.contents = commentRequestDto.getContents();
    }

    public void countLike() {
        this.likes++;
    }

    public void decreaseLike() {
        this.likes--;
    }
}
