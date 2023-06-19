package com.sparta.learnspring.Service;

import com.sparta.learnspring.Dto.BooleanDto;
import com.sparta.learnspring.Dto.RequestDto;
import com.sparta.learnspring.Dto.ResponseDto;
import com.sparta.learnspring.Entity.Post;
import com.sparta.learnspring.Repoistory.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public ResponseDto createPost(RequestDto requestDto) {
        // 제이슨 - > 엔티티화
        Post post = new Post(requestDto);

        // DB 저장
        Post savePost = postRepository.save(post);


        // postResponse 로 저장
        ResponseDto responseDto = new ResponseDto(post);
        return responseDto;
    }

    public List<ResponseDto> displayPost() {
        // DB 조회
        return postRepository.findAllByOrderByModifiedAtDesc().stream().map(ResponseDto::new).toList();
    }
    @Transactional
    public String updatePost(Long id, RequestDto requestDto) {
        // 해당 메모가 존재하는지 확인 // Optional
        Post post = findPost(id);

        post.update(requestDto);
        return "수정이 완료되었습니다.";
    }

    public BooleanDto deletePost(Long id) {
        // 해당 게시글이 존재하지는지 확인
        Post post = findPost(id);

        // 게시글 삭제
        postRepository.delete(post);
        return new BooleanDto(true);

    }

    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );
    }
}
