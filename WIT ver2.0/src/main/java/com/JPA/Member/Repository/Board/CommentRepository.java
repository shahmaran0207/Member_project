package com.JPA.Member.Repository.Board;

import org.springframework.data.jpa.repository.JpaRepository;
import com.JPA.Member.Entity.Board.CommentEntity;
import com.JPA.Member.Entity.Board.BoardEntity;
import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByBoardEntityOrderByIdDesc(BoardEntity boardEntity);
}