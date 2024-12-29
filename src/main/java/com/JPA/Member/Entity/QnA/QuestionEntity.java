package com.JPA.Member.Entity.QnA;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
@Entity
@Table(name="Question")
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "questionEntity", cascade = CascadeType.REMOVE)
    private List<AnswerEntity> answersList = new ArrayList<>();

    public void addAnswer(AnswerEntity answer) {
        answer.setQuestionEntity(this);
        answersList.add(answer);
    }
}
