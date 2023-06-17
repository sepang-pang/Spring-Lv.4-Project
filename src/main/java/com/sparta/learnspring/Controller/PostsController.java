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

    private final JdbcTemplate jdbcTemplate;

    public PostsController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 게시글 작성
    @PostMapping("/posts")
    public ResponseDto responseDto(@RequestBody RequestDto requestDto) {
        PostService postService = new PostService(jdbcTemplate);
        return postService.createPost(requestDto);
    }

    @GetMapping("/posts")
    public List<ResponseDto> displayPost() {
        PostService postService = new PostService(jdbcTemplate);
        return postService.displayPost();
    }

    @PutMapping("/posts/{id}")
    public String UpdateDto(@PathVariable Long id, @RequestBody RequestDto requestDto) {
        PostService postService = new PostService(jdbcTemplate);
        return postService.updatePost(id, requestDto);
    }


    @DeleteMapping("/posts/{id}")
    public BooleanDto booleanDto(@PathVariable Long id) {
        PostService postService = new PostService(jdbcTemplate);
        return postService.deletePost(id);
    }
}
