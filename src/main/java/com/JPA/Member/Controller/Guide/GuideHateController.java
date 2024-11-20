package com.JPA.Member.Controller.Guide;

import org.springframework.web.bind.annotation.*;
import com.JPA.Member.Service.Guide.GuideHateService;
import com.JPA.Member.DTO.Guide.GuideHateDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/hates")
@RequiredArgsConstructor
public class GuideHateController {
    private final GuideHateService guideHateService;

    @PostMapping("/toggle")
    public String toggleHate(@RequestBody GuideHateDTO guideHateDTO) {
        return guideHateService.toggleHate(guideHateDTO);
    }

    @GetMapping("/count/{guideId}")
    public int getHateCount(@PathVariable Long guideId) {
        return guideHateService.getHateCount(guideId);
    }

    @GetMapping("/status/{guideId}/{memberId}")
    public boolean checkHateCount(@PathVariable Long guideId, @PathVariable Long memberId) {
        return guideHateService.isHatedByMember(guideId, memberId);
    }
}