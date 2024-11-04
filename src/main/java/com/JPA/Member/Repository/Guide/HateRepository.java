package com.JPA.Member.Repository.Guide;

import org.springframework.data.jpa.repository.JpaRepository;
import com.JPA.Member.Entity.MemberEntity;
import com.JPA.Member.Entity.Guide.GuideEntity;
import com.JPA.Member.Entity.Guide.HateEntity;

public interface HateRepository extends JpaRepository<HateEntity, Long> {
    boolean existsByMemberEntityAndGuideEntity(MemberEntity member, GuideEntity guide);
    int countByGuideEntity(GuideEntity guide);
    void deleteByMemberEntityAndGuideEntity(MemberEntity member, GuideEntity guide);
}
