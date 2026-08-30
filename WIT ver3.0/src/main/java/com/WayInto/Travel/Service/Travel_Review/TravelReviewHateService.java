package com.WayInto.Travel.Service.Travel_Review;

import com.WayInto.Travel.Repository.Travel_Review.TravelReviewHateRepository;
import com.WayInto.Travel.Repository.Travel_Review.Travel_ReviewRepository;
import com.WayInto.Travel.Entity.Travel_Review.TravelReviewHateEntity;
import org.springframework.transaction.annotation.Transactional;
import com.WayInto.Travel.DTO.Travel_Review.TravelReviewHateDTO;
import com.WayInto.Travel.Repository.Member.MemberRepository;
import com.WayInto.Travel.Entity.Travel_Review.ReviewEntity;
import com.WayInto.Travel.Entity.Member.MemberEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TravelReviewHateService {

    private final TravelReviewHateRepository travelReviewHateRepository;
    private final MemberRepository memberRepository;
    private final Travel_ReviewRepository reviewRepository;

    @Transactional
    public String toggleHate(TravelReviewHateDTO reviewHateDTO) {
        Long reviewId = reviewHateDTO.getReviewId();
        Long memberId = reviewHateDTO.getMemberId();

        if (reviewId == null || memberId == null) {
            throw new IllegalArgumentException("Review ID and Member ID must not be null");
        }

        ReviewEntity reviewEntity = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("review not found with id: " + reviewId));
        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberId));

        if (travelReviewHateRepository.existsByMemberEntityAndReviewEntity(memberEntity, reviewEntity)) {
            travelReviewHateRepository.deleteByMemberEntityAndReviewEntity(memberEntity, reviewEntity);
            reviewEntity.decreaseHatesCount();
            reviewRepository.save(reviewEntity);
            return "Like removed";
        } else {
            TravelReviewHateEntity hate = TravelReviewHateEntity.toSaveEntity(memberEntity, reviewEntity);
            travelReviewHateRepository.save(hate);
            reviewEntity.increaseHatesCount();
            reviewRepository.save(reviewEntity);
            return "Like added";
        }
    }

    public int getHateCount(Long reviewId) {
        return travelReviewHateRepository.countByReviewEntity(reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid review ID")));
    }

    public boolean isHatedByMember(Long reviewId, Long memberId) {
        ReviewEntity reviewEntity = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Board not found with id: " + reviewId));
        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberId));

        return travelReviewHateRepository.existsByMemberEntityAndReviewEntity(memberEntity, reviewEntity);
    }
}