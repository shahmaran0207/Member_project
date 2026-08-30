package com.WayInto.Travel.Repository.Guide.Guide;

import com.WayInto.Travel.Entity.Guide.guide.GuideLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import com.WayInto.Travel.Entity.Guide.guide.GuideEntity;
import com.WayInto.Travel.Entity.Member.MemberEntity;

public interface GuideLikeRepository extends JpaRepository<GuideLikeEntity, Long> {

    boolean existsByMemberEntityAndGuideEntity(MemberEntity member, GuideEntity guide);

    int countByGuideEntity(GuideEntity guide);

    void deleteByMemberEntityAndGuideEntity(MemberEntity member, GuideEntity guide);
}
