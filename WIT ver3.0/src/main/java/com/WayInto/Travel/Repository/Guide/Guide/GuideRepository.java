package com.WayInto.Travel.Repository.Guide.Guide;

import org.springframework.data.jpa.repository.JpaRepository;
import com.WayInto.Travel.Entity.Guide.guide.GuideEntity;
import java.util.Optional;

public interface GuideRepository extends JpaRepository<GuideEntity, Long> {
    Optional<GuideEntity> findByMemberEntity_Id(Long id);
}