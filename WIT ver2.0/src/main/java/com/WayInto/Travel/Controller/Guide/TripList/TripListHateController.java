package com.WayInto.Travel.Controller.Guide.TripList;

import com.WayInto.Travel.Service.Guide.TripList.TripListHateService;
import com.WayInto.Travel.DTO.Guide.TripList.TripListHateDTO;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/trip_list_hate")
@RequiredArgsConstructor
public class TripListHateController {
    private final TripListHateService tripListHateService;

    @PostMapping("/toggle")
    public String toggleHate(@RequestBody TripListHateDTO tripListHateDTO) {
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