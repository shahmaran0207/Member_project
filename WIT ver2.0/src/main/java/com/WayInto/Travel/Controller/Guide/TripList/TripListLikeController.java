package com.WayInto.Travel.Controller.Guide.TripList;

import com.WayInto.Travel.Service.Guide.TripList.TripListLikeService;
import com.WayInto.Travel.DTO.Guide.TripList.TripListLikeDTO;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/trip_list_like")
@RequiredArgsConstructor
public class TripListLikeController {
    private final TripListLikeService tripListLikeService;

    @PostMapping("/toggle")
    public String toggleLike(@RequestBody TripListLikeDTO tripListLikeDTO) {
        return tripListLikeService.toggleLike(tripListLikeDTO);
    }

    @GetMapping("/count/{triplistId}")
    public int getLikeCount(@PathVariable Long triplistId) {
        return tripListLikeService.getLikeCount(triplistId);
    }

    @GetMapping("/status/{triplistId}/{memberId}")
    public boolean checkLikeCount(@PathVariable Long triplistId, @PathVariable Long memberId) {
        return tripListLikeService.isLikedByMember(triplistId, memberId);
    }
}