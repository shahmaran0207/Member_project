package com.JPA.Member.Entity.QnA;

import com.JPA.Member.Entity.Member.MemberEntity;
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
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "questionEntity", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<AnswerEntity> answersList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity memberEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id", nullable = false)
    private AnswerEntity answerEntity;

    public void addAnswer(AnswerEntity answer) {
        answer.setQuestionEntity(this);
        answersList.add(answer);
    }
}
