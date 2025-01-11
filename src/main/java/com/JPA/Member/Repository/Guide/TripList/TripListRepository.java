package com.JPA.Member.Repository.Guide.TripList;

import org.springframework.data.jpa.repository.JpaRepository;
import com.JPA.Member.Entity.Guide.TripList.TripListEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface TripListRepository extends JpaRepository<TripListEntity, Long> {
    Page<TripListEntity> findByGuideEntityId(Long guideId, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("update TripListEntity r set r.trip_list_hits = r.trip_list_hits + 1 where r.id = :id")
    void updateHits(@Param("id") Long id);
}
