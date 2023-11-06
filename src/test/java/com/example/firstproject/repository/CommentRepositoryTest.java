package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.JsonPath;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Test
    @DisplayName("특정 게시글의 모든 댓글 조회")
    void findByAricleId() {
        // 1. 입력 데이터 준비
        Long articleId = 4L;

        // 2. 실제 데이터
        List<Comment> comments = commentRepository.findByAricleId(articleId);

        // 3. 예상 데이터
        Article article = new Article(4L, "제목4", "내용4");
        Comment a = new Comment(1L, article, "이름1", "내용1");
        Comment b = new Comment(2L, article, "이름2", "내용2");
        //Comment c = new Comment(3L, article, "이름3", "내용3");
        List<Comment> expected = Arrays.asList(a, b);

        // 4. 비교 및 검증
        assertEquals(expected.toString(), comments.toString(), "4번 글의 모든 댓글 출력");
    }

    @Test
    @DisplayName("특정 닉네임의 모든 댓글 조회")
    void findByNickname() {
        // 1. 입력 데이터 준비
        String nickname = "이름1";

        // 2. 실제 데이터
        List<Comment> comments = commentRepository.findByNickname(nickname);

        // 3. 예상 데이터
        Comment a = new Comment(1L, new Article(4L, "제목4", "내용4"), nickname, "내용1");
        Comment b = new Comment(2L, new Article(4L, "제목4", "내용4"), "이름2", "내용2");
        //Comment c = new Comment(3L, article, "이름3", "내용3");
        List<Comment> expected = Arrays.asList(a, b);

        // 4. 비교 및 검증
        assertEquals(expected.toString(), comments.toString(), "이름1의 모든 댓글 출력");
    }
}