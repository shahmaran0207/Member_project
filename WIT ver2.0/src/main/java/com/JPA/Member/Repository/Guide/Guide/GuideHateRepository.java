package com.JPA.Member.Repository.Guide.Guide;

import org.springframework.data.jpa.repository.JpaRepository;
import com.JPA.Member.Entity.Guide.Guide.GuideHateEntity;
import com.JPA.Member.Entity.Guide.Guide.GuideEntity;
import com.JPA.Member.Entity.Member.MemberEntity;

public interface GuideHateRepository extends JpaRepository<GuideHateEntity, Long> {
    boolean existsByMemberEntityAndGuideEntity(MemberEntity member, GuideEntity guide);
    int countByGuideEntity(GuideEntity guide);
    void deleteByMemberEntityAndGuideEntity(MemberEntity member, GuideEntity guide);
}
