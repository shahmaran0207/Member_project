package com.WayInto.Travel.Repository.QnA.Question;

import com.WayInto.Travel.Entity.QnA.Question.QuestionFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionFileRepository extends JpaRepository<QuestionFileEntity, Long> {

    List<QuestionFileEntity> findByQuestionEntity_Id(Long questionEntityId);
}
