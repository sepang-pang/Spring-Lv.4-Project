package com.sparta.learnspring.Service;

import com.sparta.learnspring.Dto.BooleanDto;
import com.sparta.learnspring.Dto.RequestDto;
import com.sparta.learnspring.Dto.ResponseDto;
import com.sparta.learnspring.Entity.Post;
import com.sparta.learnspring.Repoistory.PostRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class PostService {

    private final JdbcTemplate jdbcTemplate;

    public PostService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public ResponseDto createPost(RequestDto requestDto) {
        // 제이슨 - > 엔티티화
        Post post = new Post(requestDto);

        // DB 저장
        PostRepository postRepository = new PostRepository(jdbcTemplate);
        Post savePost = postRepository.save(post);


        // postResponse 로 저장
        ResponseDto responseDto = new ResponseDto(post);
        return responseDto;
    }

    public List<ResponseDto> displayPost() {
        // DB 조회
        PostRepository postRepository = new PostRepository(jdbcTemplate);
        return postRepository.findAll();
    }

    public String updatePost(Long id, RequestDto requestDto) {
        PostRepository postRepository = new PostRepository(jdbcTemplate);
        Post post = postRepository.findById(id);

        if (post != null) {
            postRepository.update(id, requestDto);
            return "수정이 완료되었습니다.";
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

    public BooleanDto deletePost(Long id) {
        // 해당 게시글이 존재하지는지 확인
        PostRepository postRepository = new PostRepository(jdbcTemplate);
        Post post = postRepository.findById(id);

        if (post != null) {
            // 게시글 삭제
            postRepository.delete(id);
            return new BooleanDto(true);
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }
}
