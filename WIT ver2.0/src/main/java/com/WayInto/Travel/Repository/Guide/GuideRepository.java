package com.WayInto.Travel.Repository.Guide;

import org.springframework.data.jpa.repository.JpaRepository;
import com.WayInto.Travel.Entity.Guide.Guide.GuideEntity;
import java.util.Optional;

public interface GuideRepository extends JpaRepository<GuideEntity, Long> {
    Optional<GuideEntity> findByMemberEntity_Id(Long id); // 필드 이름에 맞게 메서드 변경
}