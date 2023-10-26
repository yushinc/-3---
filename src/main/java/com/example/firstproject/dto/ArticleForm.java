package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor // 클래스 내부 모든 필드의 생성자 자동 생성
@ToString // toString() 메소드 사용 가능
public class ArticleForm {
    private Long id; // id를 받을 필드
    private String title; // 제목을 받을 필드
    private String content; // 내용을 받을 필드

//    public ArticleForm(String title, String content) {
//        this.title = title;
//        this.content = content;
//    }
//
//    // 받아온 데이터 확인 위해 출력
//    @Override
//    public String toString() {
//        return "ArticleForm{" +
//                "title='" + title + '\'' +
//                ", content='" + content + '\'' +
//                '}';
//    }

    // DTO를 엔티티로 변환
    public Article toEntity() { // 엔티티 객체 생성자 양식에 맞게 작성
        return new Article(id, title, content);
    }
}
