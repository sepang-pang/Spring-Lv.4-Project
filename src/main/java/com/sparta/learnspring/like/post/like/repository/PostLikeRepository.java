package com.sparta.learnspring.like.post.like.repository;

import com.sparta.learnspring.like.post.like.entity.PostLike;
import com.sparta.learnspring.post.entity.Post;
import com.sparta.learnspring.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike>findByUserAndPost(User user, Post post);
}
