package com.sparta.learnspring.Service;

import com.sparta.learnspring.Dto.BooleanDto;
import com.sparta.learnspring.Dto.RequestDto;
import com.sparta.learnspring.Dto.ResponseDto;
import com.sparta.learnspring.Entity.Post;
import com.sparta.learnspring.Repoistory.PostRepository;
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
        return postRepository.findAll();
    }

    public String updatePost(Long id, RequestDto requestDto) {
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
