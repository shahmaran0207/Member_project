package com.JPA.Member.Repository.Board;

import org.springframework.data.jpa.repository.JpaRepository;
import com.JPA.Member.Entity.Board.BoardHateEntity;
import com.JPA.Member.Entity.Member.MemberEntity;
import com.JPA.Member.Entity.Board.BoardEntity;

public interface BoardHateRepository extends JpaRepository<BoardHateEntity, Long> {
    boolean existsByMemberEntityAndBoardEntity(MemberEntity member, BoardEntity board);
    int countByBoardEntity(BoardEntity board);
    void deleteByMemberEntityAndBoardEntity(MemberEntity member, BoardEntity board);
}
