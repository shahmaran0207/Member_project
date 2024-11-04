package com.JPA.Member.Controller;

import org.springframework.web.bind.annotation.*;
import com.JPA.Member.Service.HateService;
import lombok.RequiredArgsConstructor;
import com.JPA.Member.DTO.HateDTO;

@RestController
@RequestMapping("/api/hates")
@RequiredArgsConstructor
public class HateController {
    private final HateService hateService;

    @PostMapping("/toggle")
    public String toggleHate(@RequestBody HateDTO hateDTO) {
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