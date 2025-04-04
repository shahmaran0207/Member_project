package com.WayInto.Travel.Repository.Guide.Guide;

import com.WayInto.Travel.Entity.Guide.guide.GuideHateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import com.WayInto.Travel.Entity.Guide.guide.GuideEntity;
import com.WayInto.Travel.Entity.Member.MemberEntity;

public interface GuideHateRepository extends JpaRepository<GuideHateEntity, Long> {

    boolean existsByMemberEntityAndGuideEntity(MemberEntity member, GuideEntity guide);

    int countByGuideEntity(GuideEntity guide);

    void deleteByMemberEntityAndGuideEntity(MemberEntity member, GuideEntity guide);
}
