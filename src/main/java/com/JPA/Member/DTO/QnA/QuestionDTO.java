package com.JPA.Member.DTO.QnA;

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

    private int id;

    private String title;
    private String content;
    private String memberName;

    private LocalDateTime createDate;

    private List<AnswerEntity> answersList = new ArrayList<>();
}