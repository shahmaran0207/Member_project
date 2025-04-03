package com.WayInto.Travel.Service.Travel_Review;

import com.WayInto.Travel.Repository.Travel_Review.TravelReviewLikeRepository;
import com.WayInto.Travel.Repository.Travel_Review.Travel_ReviewRepository;
import com.WayInto.Travel.Entity.Travel_Review.TravelReviewLikeEntity;
import com.WayInto.Travel.DTO.Travel_Review.TravelReviewLikeDTO;
import org.springframework.transaction.annotation.Transactional;
import com.WayInto.Travel.Repository.Member.MemberRepository;
import com.WayInto.Travel.Entity.Travel_Review.ReviewEntity;
import com.WayInto.Travel.Entity.Member.MemberEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TravelReviewLikeService {

    private final TravelReviewLikeRepository travelReviewLikeRepository;
    private final MemberRepository memberRepository;
    private final Travel_ReviewRepository reviewRepository;

    @Transactional
    public String toggleLike(TravelReviewLikeDTO reviewLikeDTO) {
        Long reviewId = reviewLikeDTO.getReviewId();
        Long memberId = reviewLikeDTO.getMemberId();

        if (reviewId == null || memberId == null) {
            throw new IllegalArgumentException("review ID and Member ID must not be null");
        }

        ReviewEntity reviewEntity = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("review not found with id: " + reviewId));
        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberId));

        if (travelReviewLikeRepository.existsByMemberEntityAndReviewEntity(memberEntity, reviewEntity)) {
            travelReviewLikeRepository.deleteByMemberEntityAndReviewEntity(memberEntity, reviewEntity);
            reviewEntity.decreaseLikesCount();
            reviewRepository.save(reviewEntity);
            return "Like removed";
        } else {
            TravelReviewLikeEntity like = TravelReviewLikeEntity.toSaveEntity(memberEntity, reviewEntity);
            travelReviewLikeRepository.save(like);
            reviewEntity.increaseLikesCount();
            reviewRepository.save(reviewEntity);
            return "Like added";
        }
    }

    public int getLikeCount(Long reviewId) {
        return travelReviewLikeRepository.countByReviewEntity(reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid review ID")));
    }

    public boolean isLikedByMember(Long reviewId, Long memberId) {
        ReviewEntity reviewEntity = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with id: " + reviewId));
        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberId));

        return travelReviewLikeRepository.existsByMemberEntityAndReviewEntity(memberEntity, reviewEntity);
    }
}
