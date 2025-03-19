package com.JPA.Member.Repository.Travel_Review;

import com.JPA.Member.Entity.Travel_Review.TravelReviewHateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import com.JPA.Member.Entity.Travel_Review.ReviewEntity;
import com.JPA.Member.Entity.Member.MemberEntity;

public interface TravelReviewHateRepository extends JpaRepository<TravelReviewHateEntity, Long> {
    boolean existsByMemberEntityAndReviewEntity(MemberEntity member, ReviewEntity review);
    int countByReviewEntity(ReviewEntity review);
    void deleteByMemberEntityAndReviewEntity(MemberEntity member, ReviewEntity review);
}
