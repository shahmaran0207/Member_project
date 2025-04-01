package com.WayInto.Travel.Controller.Member;

import com.WayInto.Travel.DTO.Member.MemberHateDTO;
import com.WayInto.Travel.Service.Member.MemberHateService;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/member_hates")
@RequiredArgsConstructor
public class MemberHateController {
    private final MemberHateService memberHateService;

       @PostMapping("/toggle")
       public String toggleHate(@RequestBody MemberHateDTO memberHateDTO) {
           return memberHateService.toggleHate(memberHateDTO);
       }

      @GetMapping("/count/{target_hater}")
      public int getHateCount(@PathVariable Long target_hater) {
        return memberHateService.getHateCount(target_hater);
        }

      @GetMapping("/status/{target_hater}/{HaterId}")
       public boolean checkHateStatus(@PathVariable Long HaterId, @PathVariable Long target_hater) {
             return memberHateService.isHatedByMember(target_hater, HaterId);
    }
}