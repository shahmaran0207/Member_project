package com.WayInto.Travel.Controller.Travel_Review;

import com.WayInto.Travel.Service.Travel_Review.TravelReviewLikeService;
import com.WayInto.Travel.DTO.Travel_Review.TravelReviewLikeDTO;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/travel_review_like")
@RequiredArgsConstructor
public class Travel_Review_LikeController {

    private final TravelReviewLikeService reviewLikeService;

    @PostMapping("/toggle")
    public String toggleLike(@RequestBody TravelReviewLikeDTO travelReviewLikeDTO) {
        return reviewLikeService.toggleLike(travelReviewLikeDTO);
    }

    @GetMapping("/count/{travelReviewId}")
    public int getLikeCount(@PathVariable Long travelReviewId) {
        return reviewLikeService.getLikeCount(travelReviewId);
    }

    @GetMapping("/status/{travelReviewId}/{memberId}")
    public boolean checkLikeStatus(@PathVariable Long travelReviewId, @PathVariable Long memberId) {
        return reviewLikeService.isLikedByMember(travelReviewId, memberId);
    }
}

