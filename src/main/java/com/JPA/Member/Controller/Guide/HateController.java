package com.JPA.Member.Controller.Guide;

import org.springframework.web.bind.annotation.*;
import com.JPA.Member.Service.Guide.HateService;
import com.JPA.Member.DTO.Guide.HateDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/hates")
@RequiredArgsConstructor
public class HateController {
    private final HateService hateService;

    @PostMapping("/toggle")
    public String toggleHate(@RequestBody HateDTO hateDTO) {
        System.out.println(hateDTO.getGuideId());
        return hateService.toggleHate(hateDTO);
    }

    @GetMapping("/count/{guideId}")
    public int getHateCount(@PathVariable Long guideId) {
        return hateService.getHateCount(guideId);
    }

    @GetMapping("/status/{guideId}/{memberId}")
    public boolean checkHateCount(@PathVariable Long guideId, @PathVariable Long memberId) {
        return hateService.isHatedByMember(guideId, memberId);
    }
}