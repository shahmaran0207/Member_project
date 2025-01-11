package com.JPA.Member.Repository.Guide.TripList;

import org.springframework.data.jpa.repository.JpaRepository;
import com.JPA.Member.Entity.Guide.TripList.TripListEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface TripListRepository extends JpaRepository<TripListEntity, Long> {
    Page<TripListEntity> findByGuideEntityId(Long guideId, Pageable pageable);
}
