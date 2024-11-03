package com.JPA.Member.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.JPA.Member.Entity.MemberEntity;
import com.JPA.Member.Entity.BoardEntity;
import com.JPA.Member.Entity.LikeEntity;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    boolean existsByMemberEntityAndBoardEntity(MemberEntity member, BoardEntity board);
    int countByBoardEntity(BoardEntity board);
    void deleteByMemberEntityAndBoardEntity(MemberEntity member, BoardEntity board);
}
