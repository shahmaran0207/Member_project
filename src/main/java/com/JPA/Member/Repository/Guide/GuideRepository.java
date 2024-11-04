package com.JPA.Member.Repository.Guide;

import org.springframework.data.jpa.repository.JpaRepository;
import com.JPA.Member.Entity.Guide.GuideEntity;

public interface GuideRepository extends JpaRepository<GuideEntity, Long> {
}
