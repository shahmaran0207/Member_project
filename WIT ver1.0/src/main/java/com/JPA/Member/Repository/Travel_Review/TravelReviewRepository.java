package com.JPA.Member.Repository.Travel_Review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import com.JPA.Member.Entity.Travel_Review.ReviewEntity;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

public interface TravelReviewRepository extends JpaRepository<ReviewEntity, Long> {
    @Modifying(clearAutomatically = true)
    @Query("update ReviewEntity r set r.review_hits = r.review_hits + 1 where r.id = :id")
    void updateHits(@Param("id") Long id);

}