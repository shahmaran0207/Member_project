package com.WayInto.Travel.Repository.Travel_Review;

import com.WayInto.Travel.Entity.Travel_Review.ReviewFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TravelReviewFileRepository extends JpaRepository<ReviewFileEntity, Long> {

    List<ReviewFileEntity> findByReviewEntity_Id(Long id);
}
