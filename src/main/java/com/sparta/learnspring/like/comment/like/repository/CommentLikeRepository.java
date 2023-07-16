package com.sparta.learnspring.like.comment.like.repository;

import com.sparta.learnspring.comment.entity.Comment;
import com.sparta.learnspring.like.comment.like.entity.CommentLike;
import com.sparta.learnspring.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByUserAndComment(User user, Comment comment);
}
