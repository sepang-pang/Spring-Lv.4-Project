package com.sparta.learnspring.post.entity;

import com.sparta.learnspring.comment.entity.Comment;
import com.sparta.learnspring.like.post.like.entity.PostLike;
import com.sparta.learnspring.post.dto.PostRequestDto;
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
@Setter
@Getter
@DynamicInsert
@Table(name = "post")
@NoArgsConstructor
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "contents", nullable = false)
    private String contents;

    @ColumnDefault("0")
    @Column(name = "likes", nullable = false)
    private Integer likes;

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    private List<PostLike> postLikeList = new ArrayList<>();

    public void updateComments(final Comment comment) {
        commentList.add(comment);
    }

    public Post(PostRequestDto postRequestDto, Principal principal) {
        this.username = principal.getName();
        this.title = postRequestDto.getTitle();
        this.contents = postRequestDto.getContents();
    }

    public void update(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.contents = postRequestDto.getContents();
    }


    public void countLike() {
        likes++;
    }

    public void discount() {
        likes--;
    }
}