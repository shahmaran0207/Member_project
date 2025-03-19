package com.JPA.Member.Entity.QnA.Question;

import com.JPA.Member.Entity.QnA.Answer.AnswerEntity;
import com.JPA.Member.DTO.QnA.Question.QuestionDTO;
import com.JPA.Member.Entity.Member.MemberEntity;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;
import lombok.Getter;

@Entity
@Getter
@Setter
@Table(name="Question")
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String questionPass;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column
    private int questionhits;

    @Column
    private String answerStatus;

    @Column
    private int fileAttached;

    @Column
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "questionEntity", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<AnswerEntity> answersList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity memberEntity;

    @OneToMany(mappedBy = "questionEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<QuestionFileEntity> questionFileEntityList = new ArrayList<>();

    public static QuestionEntity toSaveEntity(QuestionDTO questionDTO, MemberEntity memberEntity) {
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setAnswerStatus("not answered");
        questionEntity.setQuestionPass(questionDTO.getQuestionPass());
        questionEntity.setTitle(questionDTO.getTitle());
        questionEntity.setContent(questionDTO.getContent());
        questionEntity.setCreateDate(LocalDateTime.now());
        questionEntity.setQuestionhits(0);
        questionEntity.setFileAttached(0);
        questionEntity.setMemberEntity(memberEntity);
        return questionEntity;
    }

    public static QuestionEntity toSaveFileEntity(QuestionDTO questionDTO, MemberEntity memberEntity) {
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setTitle(questionDTO.getTitle());
        questionEntity.setQuestionPass(questionDTO.getQuestionPass());
        questionEntity.setCreateDate(LocalDateTime.now());
        questionEntity.setContent(questionDTO.getContent());
        questionEntity.setAnswerStatus("not answered");
        questionEntity.setQuestionhits(0);
        questionEntity.setFileAttached(1);
        questionEntity.setMemberEntity(memberEntity);
        return questionEntity;
    }
}
