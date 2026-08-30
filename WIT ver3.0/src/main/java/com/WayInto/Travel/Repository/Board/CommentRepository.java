package com.WayInto.Travel.Repository.Board;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.WayInto.Travel.Entity.Board.CommentEntity;
import com.WayInto.Travel.Entity.Board.BoardEntity;
import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByBoardEntityOrderByIdDesc(BoardEntity boardEntity);

    @Transactional
    void deleteByBoardEntity_Id(Long boardId);
}