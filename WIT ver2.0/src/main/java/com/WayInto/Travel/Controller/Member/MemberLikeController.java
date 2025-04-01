package com.WayInto.Travel.Controller.Member;

import com.WayInto.Travel.Service.Member.MemberLikeService;
import com.WayInto.Travel.DTO.Member.MemberLikeDTO;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/member_likes")
@RequiredArgsConstructor
public class MemberLikeController {
    private final MemberLikeService memberLikeService;

    @PostMapping("/toggle")
    public String toggleLike(@RequestBody MemberLikeDTO memberLikeDTO) {
        return memberLikeService.toggleLike(memberLikeDTO);
    }

   @GetMapping("/count/{memberId}")
   public int getLikeCount(@PathVariable(name = "memberId", required = false) Long memberId) {
      return memberLikeService.getLikeCount(memberId);
  }

     @GetMapping("/status/{targetId}/{LikerId}")
     public boolean checkLikeStatus(@PathVariable Long LikerId, @PathVariable Long targetId) {
        return memberLikeService.isLikedByMember(LikerId, targetId);
     }
}