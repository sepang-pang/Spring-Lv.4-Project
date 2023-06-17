package com.sparta.learnspring.Controller;

import com.sparta.learnspring.Dto.BooleanDto;
import com.sparta.learnspring.Dto.RequestDto;
import com.sparta.learnspring.Dto.ResponseDto;
import com.sparta.learnspring.Entity.Post;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

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
        // 제이슨 - > 엔티티화
        Post post = new Post(requestDto);

        // DB 저장
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO post (name, contents, password) VALUES (?, ?, ?)";
        jdbcTemplate.update(con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(1, post.getName());
                    preparedStatement.setString(2, post.getContents());
                    preparedStatement.setInt(3, post.getPassword());
                    return preparedStatement;
                },
                keyHolder);

        // DN Insert 후 받아온 기본키 확인
        Long id = keyHolder.getKey().longValue();
        post.setId(id);

        // postResponse 로 저장
        ResponseDto responseDto = new ResponseDto(post);
        return responseDto;

    }

    @GetMapping("/posts")
    public List<ResponseDto> displayPost() {
        String sql = "SELECT * FROM post";

        return jdbcTemplate.query(sql, new RowMapper<ResponseDto>() {
            @Override
            public ResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String contents = rs.getString("contents");
                return new ResponseDto(id, name, contents);
            }
        });

    }

    @PutMapping("/posts/{id}")
    public String UpdateDto(@PathVariable Long id, @RequestBody RequestDto requestDto) {
        Post post = findById(id);
        if (post != null) {
            String sql = "UPDATE post SET name = ?, contents = ? WHERE id = ?";
            jdbcTemplate.update(sql, requestDto.getName(), requestDto.getContents(), id);

            return "수정이 완료되었습니다.";
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }


    @DeleteMapping("/posts/{id}")
    public BooleanDto booleanDto(@PathVariable Long id, @RequestBody RequestDto requestDto) {
        // 해당 게시글이 존재하지는지 확인
        Post post = findById(id);
        if (post != null) {
            // 게시글 삭제
            String sql = "DELETE FROM post WHERE id = ?";
            jdbcTemplate.update(sql, id);

            return new BooleanDto(true);
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

    private Post findById(Long id) {
        // DB 조회
        String sql = "SELECT * FROM post WHERE id = ?";
        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                Post post = new Post();
                post.setName(resultSet.getString("name"));
                post.setContents(resultSet.getString("contents"));
                return post;
            } else {
                return null;
            }
        }, id);
    }

}
