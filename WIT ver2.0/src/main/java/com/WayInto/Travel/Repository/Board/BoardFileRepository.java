package com.WayInto.Travel.Repository.Board;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.WayInto.Travel.Entity.Board.BoardFileEntity;
import com.WayInto.Travel.Entity.Board.BoardEntity;
import java.util.List;

public interface BoardFileRepository extends JpaRepository<BoardFileEntity, Long> {

    List<BoardFileEntity> findByBoardEntity_Id(Long id);

    @Transactional
    void deleteByBoardEntity_Id(Long id);

    List<BoardFileEntity> findByBoardEntity(BoardEntity existingBoardEntity);
}
