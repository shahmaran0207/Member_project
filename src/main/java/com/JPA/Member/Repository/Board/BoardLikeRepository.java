package com.JPA.Member.Repository.Board;

import org.springframework.data.jpa.repository.JpaRepository;
import com.JPA.Member.Entity.Member.MemberEntity;
import com.JPA.Member.Entity.Board.BoardEntity;
import com.JPA.Member.Entity.Board.BoardLikeEntity;

public interface BoardLikeRepository extends JpaRepository<BoardLikeEntity, Long> {
    boolean existsByMemberEntityAndBoardEntity(MemberEntity member, BoardEntity board);
    int countByBoardEntity(BoardEntity board);
    void deleteByMemberEntityAndBoardEntity(MemberEntity member, BoardEntity board);
}
