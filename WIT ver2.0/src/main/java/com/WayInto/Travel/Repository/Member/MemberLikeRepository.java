package com.WayInto.Travel.Repository.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import com.WayInto.Travel.Entity.Member.MemberLikeEntity;
import com.WayInto.Travel.Entity.Member.MemberEntity;

public interface MemberLikeRepository extends JpaRepository<MemberLikeEntity, Long> {
    boolean existsByLikerAndTarget(MemberEntity liker, MemberEntity target);

    void deleteByLikerAndTarget(MemberEntity liker, MemberEntity target);

    int countByTarget(MemberEntity target);
}
