package com.JPA.Member.Controller.Travel_Review;

import com.JPA.Member.Service.Travel_Review.TravelReviewLikeService;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/travel_review_like")
@RequiredArgsConstructor
public class Travel_Review_LikeController {

    private final TravelReviewLikeService reviewLikeService;


}

