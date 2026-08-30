package com.WayInto.Travel.Repository.Guide.Guide;

import com.WayInto.Travel.Entity.Guide.Temp_Guide.Temp_GuideEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Temp_GuideRepository extends JpaRepository<Temp_GuideEntity, Long> {

    @Transactional
    void deleteByMemberEntity_Id(Long boardId);
}