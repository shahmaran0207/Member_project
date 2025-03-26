package com.WayInto.Travel.Repository.Board;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.WayInto.Travel.Entity.Board.BoardHateEntity;
import com.WayInto.Travel.Entity.Member.MemberEntity;
import com.WayInto.Travel.Entity.Board.BoardEntity;

public interface BoardHateRepository extends JpaRepository<BoardHateEntity, Long> {
    boolean existsByMemberEntityAndBoardEntity(MemberEntity member, BoardEntity board);

    int countByBoardEntity(BoardEntity board);

    void deleteByMemberEntityAndBoardEntity(MemberEntity member, BoardEntity board);

    @Transactional
    void deleteByBoardEntity_Id(Long boardId);
}
