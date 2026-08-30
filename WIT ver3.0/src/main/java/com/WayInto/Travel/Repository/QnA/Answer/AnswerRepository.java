package com.WayInto.Travel.Repository.QnA.Answer;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.WayInto.Travel.Entity.QnA.Answer.AnswerEntity;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {

    @Transactional
    void deleteByQuestionEntity_Id(Long id);
}

