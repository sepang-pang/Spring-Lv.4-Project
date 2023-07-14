package com.sparta.learnspring.like.comment.like.repository;

import com.sparta.learnspring.like.comment.like.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
}
