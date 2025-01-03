package com.JPA.Member.Repository.QnA.Question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import com.JPA.Member.Entity.QnA.Question.QuestionEntity;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import com.JPA.Member.Entity.QnA.Answer.AnswerEntity;
import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.List;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

    QuestionEntity findByTitle(String title);

    QuestionEntity findByTitleAndContent(String title, String content);

    List<QuestionEntity> findByTitleLike(String Title);

    @Modifying
    @Transactional
    @Query(value="ALTER TABLE Question AUTO_INCREMENT=1",  nativeQuery = true)      //nativeQuery=true: true여야 MYSQL 사용이 가능
    void clearAutoIncrement();

    @Modifying(clearAutomatically = true)
    @Query("update QuestionEntity q set q.questionhits = q.questionhits + 1 where q.id = :id")
    void updateHits(@Param("id") Long id);

    @Query("SELECT a FROM AnswerEntity a WHERE a.questionEntity.id = :id")
    Optional<AnswerEntity> findByQuestionId(@Param("id") Long id);
}
