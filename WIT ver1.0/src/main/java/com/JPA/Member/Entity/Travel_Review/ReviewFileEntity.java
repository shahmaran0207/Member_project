package com.JPA.Member.Entity.Travel_Review;

import com.JPA.Member.Entity.Board.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="review_file_table")
public class ReviewFileEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String originalFileName;

    @Column
    private String storedFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private ReviewEntity reviewEntity;

    public static ReviewFileEntity toReviewFileEntity(ReviewEntity reviewEntity, String originalFileName, String storedFileName) {
        ReviewFileEntity reviewFileEntity = new ReviewFileEntity();
        reviewFileEntity.setOriginalFileName(originalFileName);
        reviewFileEntity.setStoredFileName(storedFileName);
        reviewFileEntity.setReviewEntity(reviewEntity);
        return reviewFileEntity;
    }
}