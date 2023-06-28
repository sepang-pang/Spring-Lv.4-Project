package com.sparta.learnspring.entity;

import com.sparta.learnspring.dto.CommentResponseDto;
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
public class Comment extends com.sparta.learnspring.entity.Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "contents", nullable = false)
    private String contents;

    private Comment(CommentResponseDto commentResponseDto, Principal principal) {
        this.username = principal.getName();
        this.contents = commentResponseDto.getContents();
    }

}
