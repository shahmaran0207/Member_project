package com.JPA.Member.Repository.Travel_Review;

import com.JPA.Member.Entity.Travel_Review.TravelReviewLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import com.JPA.Member.Entity.Travel_Review.ReviewEntity;
import com.JPA.Member.Entity.Member.MemberEntity;

public interface TravelReviewLikeRepository extends JpaRepository<TravelReviewLikeEntity, Long> {
    boolean existsByMemberEntityAndReviewEntity(MemberEntity member, TravelReviewLikeEntity review);
    int countByReviewEntity(ReviewEntity review);
    void deleteByMemberEntityAndReviewEntity(MemberEntity member, ReviewEntity review);
}
