package com.example.firstproject.entity;

import com.example.firstproject.Service.ArticleService;
import com.example.firstproject.dto.CommentDto;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
@Entity
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="article_id") // 게시글의 PK와 매핑
    private Article aricle;

    @Column
    private String nickname;

    @Column
    private String body;

    // 댓글 생성
    public static Comment createComment(CommentDto dto, Article article) {

        // 예외 발생
        // 1. dto에 생성할 댓글의 id가 이미 존재하는 경우
        if (dto.getId() != null)
            throw new IllegalArgumentException("댓글 생성 실패! 댓글의 id가 이미 존재합니다.");

        // 2. dto에서 가져온 부모 게시글의 id와 엔티티에서 가져온 부모 게시글의 id가 다른 경우
        if (dto.getArticleId() != article.getId())
            throw new IllegalArgumentException("댓글 생성 실패! 게시글의 id가 잘못되었습니다.");

        // 엔티티 생성 및 반환
        return new Comment(
                dto.getId(),
                article,
                dto.getNickname(),
                dto.getBody()
        );
    }

    // 댓글 수정
    public void patch(CommentDto dto) {

        // 예외 발생
        // url의 댓글 id와 JSON 데이터의 댓글 id가 다른 경우
        if (this.id != dto.getId())
            throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력되었습니다.");

        // 객체 갱신
        if (dto.getNickname() != null)
            this.nickname = dto.getNickname();
        if (dto.getBody() != null)
            this.body = dto.getBody();
    }
}
