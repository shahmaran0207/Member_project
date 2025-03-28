package com.WayInto.Travel.Repository.Travel_Review;

import com.WayInto.Travel.Entity.Travel_Review.ReviewFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelReviewFileRepository extends JpaRepository<ReviewFileEntity, Long> {
}
