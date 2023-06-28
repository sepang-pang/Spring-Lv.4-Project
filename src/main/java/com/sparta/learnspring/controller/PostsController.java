package com.sparta.learnspring.controller;

import com.sparta.learnspring.dto.MsgDto;
import com.sparta.learnspring.dto.PostRequestDto;
import com.sparta.learnspring.dto.PostResponseDto;
import com.sparta.learnspring.entity.UserRoleEnum;
import com.sparta.learnspring.service.PostService;
import org.springframework.security.access.annotation.Secured;
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
    @Secured(UserRoleEnum.Authority.USER)
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
    @GetMapping("/posts/{username}")
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
    public MsgDto msgDto(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, Principal principal) {
        return postService.deletePost(id, postRequestDto, principal);
    }
}
