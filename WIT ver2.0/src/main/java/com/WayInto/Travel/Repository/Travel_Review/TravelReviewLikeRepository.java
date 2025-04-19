package com.WayInto.Travel.Repository.Travel_Review;

import com.WayInto.Travel.Entity.Travel_Review.TravelReviewLikeEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.WayInto.Travel.Entity.Travel_Review.ReviewEntity;
import com.WayInto.Travel.Entity.Member.MemberEntity;

public interface TravelReviewLikeRepository extends JpaRepository<TravelReviewLikeEntity, Long> {
    boolean existsByMemberEntityAndReviewEntity(MemberEntity member, ReviewEntity review);

    int countByReviewEntity(ReviewEntity review);

    void deleteByMemberEntityAndReviewEntity(MemberEntity member, ReviewEntity review);

    @Transactional
    void deleteByReviewEntity_Id(Long boardId);
}
