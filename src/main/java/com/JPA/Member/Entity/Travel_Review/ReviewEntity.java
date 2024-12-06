package com.JPA.Member.Entity.Travel_Review;

import com.JPA.Member.DTO.Travel_Review.ReviewDTO;
import com.JPA.Member.Entity.Member.MemberEntity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="travel_review_table")
public class ReviewEntity extends ReviewBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String reviewpass;

    @Column
    private int likesCount = 0;

    @Column
    private int hatesCount = 0;

    @Column
    private int review_hits;

    @Column
    private int fileAttached;

    @Column
    private String address;

    @Column
    @ElementCollection
    private List<Integer> zipcodeList = new ArrayList<>();

    @OneToMany(mappedBy = "reviewEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ReviewFileEntity> reviewFileEntityList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity memberEntity;

    public void increaseLikesCount() {
        this.likesCount++;
    }

    public void decreaseLikesCount() {
        if (this.likesCount > 0) {
            this.likesCount--;
        }
    }

    public void increaseHatesCount() {
        this.hatesCount++;
    }

    public void decreaseHatesCount() {
        if (this.hatesCount > 0) {
            this.hatesCount--;
        }
    }

    public static ReviewEntity toSaveEntity(ReviewDTO reviewDTO, MemberEntity memberEntity) {
        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setReviewpass(reviewDTO.getReviewpass());
        reviewEntity.setTitle(reviewDTO.getTitle());
        reviewEntity.setContent(reviewDTO.getContent());
        reviewEntity.setMemberEntity(memberEntity);
        reviewEntity.setAddress(reviewDTO.getAddress());
        reviewEntity.setFileAttached(0);
        reviewEntity.setHatesCount(0);
        reviewEntity.setLikesCount(0);
        reviewEntity.setReview_hits(0);
        reviewEntity.setZipcodeList(reviewDTO.getZipcodeList());
        return reviewEntity;
    }

    public static ReviewEntity toSaveFileEntity(ReviewDTO reviewDTO, MemberEntity memberEntity) {
        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setReview_hits(0);
        reviewEntity.setHatesCount(reviewEntity.getHatesCount());
        reviewEntity.setLikesCount(reviewEntity.getLikesCount());
        reviewEntity.setReviewpass(reviewDTO.getReviewpass());
        reviewEntity.setContent(reviewDTO.getContent());
        reviewEntity.setAddress(reviewDTO.getAddress());
        reviewEntity.setTitle(reviewDTO.getTitle());
        reviewEntity.setMemberEntity(memberEntity);
        reviewEntity.setFileAttached(1);
        reviewEntity.setZipcodeList(reviewDTO.getZipcodeList());
        return reviewEntity;
    }

    public static ReviewEntity toUpdateEntity(ReviewDTO reviewDTO, MemberEntity memberEntity) {
        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setReview_hits(reviewEntity.getReview_hits());
        reviewEntity.setReviewpass(reviewDTO.getReviewpass());
        reviewEntity.setContent(reviewDTO.getContent());
        reviewEntity.setAddress(reviewDTO.getAddress());
        reviewEntity.setTitle(reviewDTO.getTitle());
        reviewEntity.setMemberEntity(memberEntity);
        reviewEntity.setId(reviewDTO.getId());
        reviewEntity.setZipcodeList(reviewDTO.getZipcodeList());
        return reviewEntity;
    }
}