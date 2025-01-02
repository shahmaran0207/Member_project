package com.JPA.Member.DTO.QnA;

import com.JPA.Member.Entity.QnA.QuestionEntity;
import com.JPA.Member.Entity.QnA.AnswerEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuestionDTO {

    private Long id;
    private int questionhits;

    private String title;
    private String content;
    private String memberName;
    private String answerStatus;

    private LocalDateTime createDate;

    private List<AnswerEntity> answersList = new ArrayList<>();

    public QuestionDTO(Long id, String title, String memberName, LocalDateTime createDate, int questionhits, String answerStatus) {
        this.id = id;
        this.title = title;
        this.memberName = memberName;
        this.createDate = createDate;
        this.questionhits = questionhits;
        this.answerStatus = answerStatus;
    }

    public static QuestionDTO toQuestionDTO(QuestionEntity questionEntity) {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(questionEntity.getId());
        questionDTO.setTitle(questionEntity.getTitle());
        questionDTO.setContent(questionEntity.getContent());
        questionDTO.setMemberName(questionEntity.getMemberEntity().getMemberName());
        questionDTO.setCreateDate(questionEntity.getCreateDate());
        questionDTO.setQuestionhits(questionEntity.getQuestionhits());
        questionDTO.setAnswerStatus(questionEntity.getAnswerStatus());

        return questionDTO;
    }
}