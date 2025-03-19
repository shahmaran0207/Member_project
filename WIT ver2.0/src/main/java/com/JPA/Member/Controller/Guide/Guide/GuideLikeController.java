package com.JPA.Member.Controller.Guide.Guide;

import com.JPA.Member.Service.Guide.Guide.GuideLikeService;
import com.JPA.Member.DTO.Guide.guide.GuideLikeDTO;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/guidelikes")
@RequiredArgsConstructor
public class GuideLikeController {
    private final GuideLikeService likeService;

    @PostMapping("/toggle")
    public String toggleLike(@RequestBody GuideLikeDTO guideLikeDTO) {
        return likeService.toggleLike(guideLikeDTO);
    }

    @GetMapping("/count/{guideId}")
    public int getLikeCount(@PathVariable Long guideId) {
        return likeService.getLikeCount(guideId);
    }

    @GetMapping("/status/{guideId}/{memberId}")
    public boolean checkLikeCount(@PathVariable Long guideId, @PathVariable Long memberId) {
        return likeService.isLikedByMember(guideId, memberId);
    }
}