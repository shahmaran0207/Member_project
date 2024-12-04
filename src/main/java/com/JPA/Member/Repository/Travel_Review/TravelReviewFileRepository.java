package com.JPA.Member.Repository.Travel_Review;

import org.springframework.data.jpa.repository.JpaRepository;
import com.JPA.Member.Entity.Travel_Review.ReviewFileEntity;

public interface TravelReviewFileRepository extends JpaRepository<ReviewFileEntity, Long> {
}
