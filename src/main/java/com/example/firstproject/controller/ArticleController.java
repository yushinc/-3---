package com.example.firstproject.controller;

import com.example.firstproject.Service.CommentService;
import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentService commentService;

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

        return "redirect:/articles/" + saved.getId();
    }

    // 데이터 개별 조회
    @GetMapping("/articles/{id}") // 데이터 조회 요청 접수
    public String show(@PathVariable Long id, Model model) {
        log.info("id = " + id);
        // 레포지토리로 id를 조회해 엔티티 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 특정 게시글의 댓글 조회
        List<CommentDto> commentsDtos = commentService.comments(id);

        // 모델에 엔티티 등록하기
        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDtos", commentsDtos);

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

    // 데이터 수정 폼 조회
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {

        // id로 DB에서 엔티티 조회
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 모델에 데이터 등록
        model.addAttribute("article", articleEntity);

        return "articles/edit";
    }

    // 데이터 수정
    @PostMapping("/articles/update")
    public String update(ArticleForm form) {
        log.info(form.toString());

        // 1. DTO를 엔티티로 변환
        Article article = form.toEntity();
        log.info(article.toString());

        // 2. 엔티티 DB에 저장하기
        // 2-1. DB에서 기존 데이터 가져오기
        Article target = articleRepository.findById(article.getId()).orElse(null);
        // 2-2. 기존 데이터 값 갱신하기
        if (target != null) {
            articleRepository.save(article); // 엔티티 갱신
        }

        return "redirect:/articles/" + article.getId();
    }

    // 데이터 삭제
    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("삭제 요청이 들어왔습니다.");

        // 1. 삭제할 대상 가져오기
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());
        // 2. 대상 엔티티 삭제하기
        articleRepository.delete(target);
        rttr.addFlashAttribute("msg", "삭제됐습니다!");
        // 3. 결과 페이지로 리다이렉트
        return "redirect:/articles";
    }
}
