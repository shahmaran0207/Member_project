package com.JPA.Member.DTO.Travel_Review;

import lombok.Data;

@Data
public class TravelReviewLikeDTO {
    private Long id;
    private Long memberId;
    private Long reviewId;
}
