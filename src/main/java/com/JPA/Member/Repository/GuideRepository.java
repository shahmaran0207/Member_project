package com.JPA.Member.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.JPA.Member.Entity.GuideEntity;

public interface GuideRepository extends JpaRepository<GuideEntity, Long> {
}
