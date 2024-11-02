package com.JPA.Member.Repository;

import com.JPA.Member.Entity.BoardEntity;
import com.JPA.Member.Entity.LikeEntity;
import com.JPA.Member.Entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    boolean existsByMemberEntityAndBoardEntity(MemberEntity member, BoardEntity board);
    int countByBoardEntity(BoardEntity board);
    void deleteByMemberEntityAndBoardEntity(MemberEntity member, BoardEntity board);
}
