package com.JPA.Member.Controller.Travel_Review;

import com.JPA.Member.Service.Travel_Review.TravelReviewHateService;
import com.JPA.Member.DTO.Travel_Review.TravelReviewHateDTO;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/travel_review_hate")
@RequiredArgsConstructor
public class Travel_Review_HateController {
    private final TravelReviewHateService travelReviewHateService;

    @PostMapping("/toggle")
    public String toggleHate(@RequestBody TravelReviewHateDTO reviewHateDTO) {
        return travelReviewHateService.toggleHate(reviewHateDTO);
    }

    @GetMapping("/count/{travelReviewId}")
    public int getHateCount(@PathVariable Long travelReviewId) {
        return travelReviewHateService.getHateCount(travelReviewId);
    }

    @GetMapping("/status/{travelReviewId}/{memberId}")
    public boolean checkHateCount(@PathVariable Long travelReviewId, @PathVariable Long memberId) {
        return travelReviewHateService.isHatedByMember(travelReviewId, memberId);
    }
}