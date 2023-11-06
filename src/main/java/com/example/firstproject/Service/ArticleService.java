package com.example.firstproject.Service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    // 전체 목록 조회
    public List<Article> index() {
        return articleRepository.findAll();
    }

    // 단일 게시글 조회
    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    // 게시글 생성
    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();

        // id는 DB가 자동 생성 하므로 사용자에게 입력 받을 필요가 없다.
        // 입력이 들어온 경우(= null이 아닌경우) null 반환
        if (article.getId() != null) {
            return null;
        }
        return articleRepository.save(article);
    }

    // 게시글 수정
    public Article update(Long id, ArticleForm dto) {

        // 1. DTO -> 엔티티 변환하기
        Article article = dto.toEntity();

        // 2. DB에 대상 엔티티 존재하는지 조회
        Article target = articleRepository.findById(id).orElse(null);

        // 3. 대상 엔티티가 없거나 url로 요청한 id와 바디로 담긴 수정하려는 게시글의 id가 다를 경우 처리
        if (target == null || id != article.getId()) {
            return null;
        }

        // 4. 대상 엔티티가 있다면 수정 반영 후 응답 코드 200 반환
        target.patch(article); // 기존 데이터에 새 데이터 붙이기
        Article updated = articleRepository.save(target);
        return updated;
    }

    // 게시글 삭제
    public Article delete(Long id) {
        // 1. 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);

        // 2. 잘못된 요청 처리하기
        if (target == null) {
            return null;
        }

        // 3. 대상 엔티티가 있다면 삭제 후 응답 코드 200 반환
        articleRepository.delete(target);
        return target;
    }
}
