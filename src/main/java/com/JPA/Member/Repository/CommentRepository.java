package com.JPA.Member.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.JPA.Member.Entity.CommentEntity;
import com.JPA.Member.Entity.BoardEntity;
import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByBoardEntityOrderByIdDesc(BoardEntity boardEntity);
}