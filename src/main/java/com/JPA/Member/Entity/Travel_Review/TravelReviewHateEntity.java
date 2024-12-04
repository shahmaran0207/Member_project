package com.JPA.Member.Entity.Travel_Review;

import com.JPA.Member.Entity.Member.MemberEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "travel_review_hate_table")
public class TravelReviewHateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_review_id", nullable = false)
    private ReviewEntity reviewEntity;

    public static TravelReviewHateEntity toSaveEntity(MemberEntity member, ReviewEntity review) {
        TravelReviewHateEntity reviewLikeEntity = new TravelReviewHateEntity();
        reviewLikeEntity.setMemberEntity(member);
        reviewLikeEntity.setReviewEntity(review);
        return reviewLikeEntity;
    }
}