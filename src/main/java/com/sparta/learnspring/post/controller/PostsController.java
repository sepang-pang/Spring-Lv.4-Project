package com.sparta.learnspring.post.controller;

import com.sparta.learnspring.post.dto.PostRequestDto;
import com.sparta.learnspring.post.dto.PostResponseDto;
import com.sparta.learnspring.advice.exception.RestApiException;
import com.sparta.learnspring.post.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController // 내부에서 해당 컨트롤러를 사용하고 있다 !
@RequestMapping("/api")
public class PostsController {
// 컨트롤러 -> 서비스 -> 리포지토리
    private final PostService postService;

    public PostsController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 작성
    @PostMapping("/posts")
    public PostResponseDto responseDto(@RequestBody PostRequestDto postRequestDto, Principal principal) {
        return postService.createPost(postRequestDto, principal);
}

    // 게시글 조회
    @GetMapping("/posts")
    public List<PostResponseDto> displayPost() {
        return postService.displayPost();
    }

    // 선택 게시글 조회
    @GetMapping("/posts/{username}") // RequestParam 방식으로 수정
    public List<PostResponseDto> selectDisplayPost(@PathVariable String username) {
        return postService.selectDisplayPost(username);
    }

    // 게시슬 수정
    @PutMapping("/posts/{id}")
    public List<PostResponseDto> UpdateDto(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, Principal principal) {
        return postService.updatePost(id, postRequestDto, principal);
    }

    // 게시글 삭제
    @DeleteMapping("/posts/{id}")
    public RestApiException msgDto(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, Principal principal) {
        return postService.deletePost(id, postRequestDto, principal);
    }
}
