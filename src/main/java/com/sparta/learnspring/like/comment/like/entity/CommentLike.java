package com.sparta.learnspring.like.comment.like.entity;

import com.sparta.learnspring.comment.entity.Comment;
import com.sparta.learnspring.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "comment_like", uniqueConstraints = {
        @UniqueConstraint(name = "UniqueLikeUser", columnNames = {"user_id", "comment_id"})
})
@NoArgsConstructor
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public CommentLike(User user, Comment comment) {
        this.user = user;
        this.comment = comment;
    }
}
