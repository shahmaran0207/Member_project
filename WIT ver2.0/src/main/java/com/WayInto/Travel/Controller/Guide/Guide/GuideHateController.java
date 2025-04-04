package com.WayInto.Travel.Controller.Guide.Guide;

import com.WayInto.Travel.Service.Guide.Guide.GuideHateService;
import com.WayInto.Travel.DTO.Guide.guide.GuideHateDTO;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/guidehates")
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