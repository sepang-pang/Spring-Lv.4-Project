package com.sparta.learnspring.Controller;

import com.sparta.learnspring.Dto.BooleanDto;
import com.sparta.learnspring.Dto.RequestDto;
import com.sparta.learnspring.Dto.ResponseDto;
import com.sparta.learnspring.Service.PostService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostsController {

    private final PostService postService;

    public PostsController(JdbcTemplate jdbcTemplate) {
        this.postService = new PostService(jdbcTemplate);
    }

    // 게시글 작성
    @PostMapping("/posts")
    public ResponseDto responseDto(@RequestBody RequestDto requestDto) {
        return postService.createPost(requestDto);
    }

    // 게시글 조회
    @GetMapping("/posts")
    public List<ResponseDto> displayPost() {
        return postService.displayPost();
    }

    // 게시슬 수정
    @PutMapping("/posts/{id}")
    public String UpdateDto(@PathVariable Long id, @RequestBody RequestDto requestDto) {
        return postService.updatePost(id, requestDto);
    }

    // 게시글 삭제
    @DeleteMapping("/posts/{id}")
    public BooleanDto booleanDto(@PathVariable Long id) {
        return postService.deletePost(id);
    }
}
