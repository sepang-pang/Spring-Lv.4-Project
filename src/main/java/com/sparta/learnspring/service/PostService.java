package com.sparta.learnspring.service;

import com.sparta.learnspring.dto.PostDeleteResponseDto;
import com.sparta.learnspring.dto.RequestDto;
import com.sparta.learnspring.dto.ResponseDto;
import com.sparta.learnspring.entity.Post;
import com.sparta.learnspring.repoistory.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    // 입력
    public ResponseDto createPost(RequestDto requestDto) {
        // 제이슨 - > 엔티티화
        Post post = new Post(requestDto);

        // DB 저장
        Post savePost = postRepository.save(post);

        // postResponse 로 저장
        ResponseDto responseDto = new ResponseDto(post);
        return responseDto;
    }

    // 조회
    public List<ResponseDto> displayPost() {
        // DB 조회
        return postRepository.findAllByOrderByModifiedAtDesc().stream().map(ResponseDto::new).toList();
    }

    // 선택 조회
    public List<ResponseDto> selectDisplayPost(String username) {
        // DB 조회
        return postRepository.findAllByUsername(username).stream().map(ResponseDto::new).toList();
    }


    @Transactional
    public String updatePost(Long id, RequestDto requestDto) {
        // 해당 메모가 존재하는지 확인 // Optional
        Post post = findPost(id);

        // 패스워드 확인
        if (post.getPassword().equals(requestDto.getPassword())) {
            post.update(requestDto);
            return "수정이 완료되었습니다.";
        } else {
            new IllegalArgumentException("비밀번호가 존재하지 않습니다.");
            return "수정이 되지 않았습니다";
        }

    }

    public PostDeleteResponseDto deletePost(Long id, RequestDto requestDto) {
        // 해당 게시글이 존재하지는지 확인
        Post post = findPost(id);

        // 패스워드 확인
        if (post.getPassword().equals(requestDto.getPassword())) {
            // 게시글 삭제
            postRepository.delete(post);
            return new PostDeleteResponseDto(true);
        } else {
            new IllegalArgumentException("비밀번호가 존재하지 않습니다.");
            return new PostDeleteResponseDto(false);
        }

    }

    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );
    }
}
