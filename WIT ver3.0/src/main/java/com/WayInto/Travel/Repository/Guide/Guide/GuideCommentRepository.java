package com.WayInto.Travel.Repository.Guide.Guide;

import com.WayInto.Travel.Entity.Guide.guide.GuideCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import com.WayInto.Travel.Entity.Guide.guide.GuideEntity;
import java.util.List;

public interface GuideCommentRepository extends JpaRepository<GuideCommentEntity, Long> {
    List<GuideCommentEntity> findAllByGuidecommentTargetOrderByIdDesc(GuideEntity guideEntity);
}
