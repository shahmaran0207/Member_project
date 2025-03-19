package com.JPA.Member.Controller.Guide.TripList;

import com.JPA.Member.Service.Guide.TripList.TripListHateService;
import com.JPA.Member.DTO.Guide.TripList.TripListHateDTO;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/trip_list_hate")
@RequiredArgsConstructor
public class TripListHateController {
    private final TripListHateService tripListHateService;

    @PostMapping("/toggle")
    public String toggleHate(@RequestBody TripListHateDTO tripListHateDTO) {
        System.out.println(tripListHateDTO);
        return tripListHateService.toggleHate(tripListHateDTO);
    }

    @GetMapping("/count/{TripListId}")
    public int getHateCount(@PathVariable Long TripListId) {
        return tripListHateService.getHateCount(TripListId);
    }

    @GetMapping("/status/{TripListId}/{memberId}")
    public boolean checkHateCount(@PathVariable Long TripListId, @PathVariable Long memberId) {
        return tripListHateService.isHatedByMember(TripListId, memberId);
    }
}