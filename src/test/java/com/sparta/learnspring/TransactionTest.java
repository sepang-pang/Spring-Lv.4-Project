package com.sparta.learnspring;

import com.sparta.learnspring.entity.Post;
import com.sparta.learnspring.repoistory.PostRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class TransactionTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    PostRepository postRepository;

    @Test
    @Transactional
    @Rollback(value = false) // 테스트 코드에서 @Transactional 를 사용하면 테스트가 완료된 후 롤백하기 때문에 false 옵션 추가
    @DisplayName("메모 생성 성공")
    void test1() {
        Post memo = new Post();
        memo.setName("Robbert");
        memo.setContents("@Transactional 테스트 중!");
        memo.setPassword(1234);

        em.persist(memo);  // 영속성 컨텍스트에 메모 Entity 객체를 저장합니다.
    }

    @Test
    @Disabled
    @DisplayName("메모 생성 실패")
    void test2() {
        Post post = new Post();
        post.setName("Robbie");
        post.setContents("@Transactional 테스트 중!");
        post.setPassword(1234);

        em.persist(post);  // 영속성 컨텍스트에 메모 Entity 객체를 저장합니다.
    }

    @Test
    @Disabled
//    @Transactional
//    @Rollback(value = false)
    @DisplayName("트랜잭션 전파 테스트")
    void test3() {
//        postRepository.createPost(em);
        System.out.println("테스트 test3 메서드 종료");
    }
}