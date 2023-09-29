package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Slf4j
@Controller
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    // 폼 출력
    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    // 폼 데이터 받아서 create
    @PostMapping("/articles/create")
    public String createArticle(ArticleForm articleForm) {
        log.info(articleForm.toString());
        // System.out.println(articleForm.toString());

        // 1. DTO를 엔티티로 변환
        Article article = articleForm.toEntity();
        log.info(article.toString());
        // System.out.println(article.toString()); // DTO가 엔티티로 잘 변환되는지 확인

        // 2. 레포지토리를 통해 DB에 변환한 엔티티 저장
        Article saved = articleRepository.save(article);
        log.info(saved.toString());
        // System.out.println(saved.toString()); // 엔티티가 DB에 잘 저장되는지 확인

        return "";
    }

    // 데이터 개별 조회
    @GetMapping("/articles/{id}") // 데이터 조회 요청 접수
    public String show(@PathVariable Long id, Model model) {
        log.info("id = " + id);
        // 레포지토리로 id를 조회해 엔티티 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 모델에 엔티티 등록하기
        model.addAttribute("article", articleEntity);

        // 뷰 페이지 설정하기
        return "articles/show";
    }

    // 데이터 전체 조회
    @GetMapping("/articles")
    public String index(Model model) {
        // 모든 데이터 가져오기
        ArrayList<Article> articleList = articleRepository.findAll();

        // 모델에 데이터 등록하기
        model.addAttribute("articleList", articleList);

        // 뷰 페이지 설정하기
        return "articles/index";
    }
}
