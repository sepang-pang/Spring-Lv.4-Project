package com.sparta.learnspring.Repoistory;

import com.sparta.learnspring.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
