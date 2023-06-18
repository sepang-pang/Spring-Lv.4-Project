package com.sparta.learnspring.Repoistory;

import com.sparta.learnspring.Dto.RequestDto;
import com.sparta.learnspring.Dto.ResponseDto;
import com.sparta.learnspring.Entity.Post;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class PostRepository {
    private final JdbcTemplate jdbcTemplate;
    public PostRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Post save(Post post) {
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

        return post;
    }

    public List<ResponseDto> findAll() {
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

    public void update(Long id, RequestDto requestDto) {
        String sql = "UPDATE post SET name = ?, contents = ? WHERE id = ?";
        jdbcTemplate.update(sql, requestDto.getName(), requestDto.getContents(), id);
    }

    public void delete(Long id) {
        String sql = "DELETE FROM post WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
    public Post findById(Long id) {
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
    @Transactional
    public Post createPost(EntityManager em) {
        Post post = em.find(Post.class, 1);
        post.setName("Robbie");
        post.setContents("@Transactional 전파 테스트 중!");
        post.setPassword(1234);

        System.out.println("createMemo 메서드 종료");
        return post;
    }

}
