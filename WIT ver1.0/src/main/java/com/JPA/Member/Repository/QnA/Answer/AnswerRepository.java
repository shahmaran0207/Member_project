package com.JPA.Member.Repository.QnA.Answer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.JPA.Member.Entity.QnA.Answer.AnswerEntity;
import jakarta.transaction.Transactional;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {

    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE  Answer AUTO_INCREMENT=1", nativeQuery = true)
    void autoIncrement();
}
