package com.JPA.Member.Repository.QnA;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.JPA.Member.Entity.QnA.QuestionEntity;
import jakarta.transaction.Transactional;
import java.util.List;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

    QuestionEntity findByTitle(String title);

    QuestionEntity findByTitleAndContent(String title, String content);

    List<QuestionEntity> findByTitleLike(String Title);

    @Modifying
    @Transactional
    @Query(value="ALTER TABLE Question AUTO_INCREMENT=1",  nativeQuery = true)      //nativeQuery=true: true여야 MYSQL 사용이 가능
    void clearAutoIncrement();
}
