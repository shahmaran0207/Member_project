package com.JPA.Member.Repository.Guide;

import org.springframework.data.jpa.repository.JpaRepository;
import com.JPA.Member.Entity.Guide.GuideLikeEntity;
import com.JPA.Member.Entity.Member.MemberEntity;
import com.JPA.Member.Entity.Guide.GuideEntity;

public interface GuideLikeRepository extends JpaRepository<GuideLikeEntity, Long> {
    boolean existsByMemberEntityAndGuideEntity(MemberEntity member, GuideEntity guide);
    int countByGuideEntity(GuideEntity guide);
    void deleteByMemberEntityAndGuideEntity(MemberEntity member, GuideEntity guide);
}
