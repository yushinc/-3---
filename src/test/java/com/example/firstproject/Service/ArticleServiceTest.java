package com.example.firstproject.Service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class ArticleServiceTest {

    @Autowired
    ArticleService articleService;

    // 전체 조회 테스트
    @Test
    @DisplayName("전체 조회 테스트")
    void index() {
        // 1. 예상 데이터 작성 (실제 data.sql 의 데이터와 비교해야 하므로 같도록 예상 데이터를 넣어준다.)
        Article a = new Article(1L, "제목1", "내용1");
        Article b = new Article(2L, "제목2", "내용2");
        Article c = new Article(3L, "제목3", "내용3");
        List<Article> expected = new ArrayList<>(Arrays.asList(a, b, c));

        // 2. 실제 데이터 획득 (data.sql 의 데이터)
        List<Article> articles = articleService.index();

        // 3. 예상 데이터와 실제 데이터 비교해 검증
        assertEquals(expected.toString(), articles.toString());
    }

    // 단일 조회 테스트
    @Test
    @DisplayName("단일 조회 테스트")
    void show_success() {
        // 1. 에상 데이터
        Long id = 1L;
        Article expected = new Article(id, "제목1", "내용1");

        // 2. 실제 데이터
        Article article = articleService.show(id);

        // 3. 비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @DisplayName("단일 조회 테스트_존재하지 않는 id")
    void show_fail() {
        // 1. 에상 데이터
        Long id = -1L;
        Article expected = null;

        // 2. 실제 데이터
        Article article = articleService.show(id);

        // 3. 비교 및 검증
        assertEquals(expected, article);
    }

    // 생성 테스트
    @Test
    @DisplayName("생성 테스트_dto에 제목 내용만")
    void create_success() {
        // 1. 에상 데이터
        String title = "제목4";
        String content = "내용4";
        ArticleForm dto = new ArticleForm(null, title, content);
        Article expected = new Article(4L, title, content);

        // 2. 실제 데이터
        Article article = articleService.create(dto);

        // 3. 비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @DisplayName("생성 테스트_dto에 id도 존재")
    void create_fail() {
        // 1. 에상 데이터
        Long id = 4L;
        String title = "제목4";
        String content = "내용4";
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = null;

        // 2. 실제 데이터
        Article article = articleService.create(dto);

        // 3. 비교 및 검증
        assertEquals(expected, article);
    }

}