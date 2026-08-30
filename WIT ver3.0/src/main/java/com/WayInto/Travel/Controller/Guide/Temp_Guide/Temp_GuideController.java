package com.WayInto.Travel.Controller.Guide.Temp_Guide;

import com.WayInto.Travel.Controller.ControllerAdvice.GlobalControllerAdvice;
import com.WayInto.Travel.Service.Guide.Guide.Temp_GuideService;
import com.WayInto.Travel.DTO.Guide.Temp_Guide.Temp_GuideDTO;
import com.WayInto.Travel.Service.Guide.Guide.GuideService;
import com.WayInto.Travel.Service.Member.MemberService;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.data.domain.Pageable;
import jakarta.servlet.http.HttpServletRequest;
import com.WayInto.Travel.DTO.Member.MemberDTO;
import org.springframework.data.domain.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/Temp_Guide")
public class Temp_GuideController {

    private final GlobalControllerAdvice globalControllerAdvice;
    private final Temp_GuideService tempGuideService;
    private final MemberService memberService;
    private final GuideService guideService;

    @GetMapping("/paging")
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model, @CookieValue(value = "memberRole", defaultValue = "") String memberRole) {

        int Role = Integer.parseInt(memberRole);

        if (Role != 2) {
            model.addAttribute("msg", "관리자만 이동 가능한 페이지입니다.");
            model.addAttribute("redirectUrl", "/");
            return "alert";
        }

        Page<Temp_GuideDTO> tempGuideDTOS = tempGuideService.paging(pageable);
        int blockLimit = 10;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < tempGuideDTOS.getTotalPages()) ? startPage + blockLimit - 1 : tempGuideDTOS.getTotalPages();

        model.addAttribute("tempGuideDTOS", tempGuideDTOS);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "Temp_Guide/paging";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable(name = "id") Long id, Model model,
                           @CookieValue(value = "loginId", defaultValue = "") String loginId,
                           @CookieValue(value = "loginName", defaultValue = "") String loginName) {


        model.addAttribute("loginId", loginId);
        model.addAttribute("loginName", loginName);
        Temp_GuideDTO tempGuideDTO = tempGuideService.findById(id);
        model.addAttribute("tempGuideDTO", tempGuideDTO);
        return "Temp_Guide/detail";
    }

    @GetMapping("/confirm/{id}")
    public String confirm(@PathVariable(name = "id") Long id, Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "memberId", required = false) Long tempGuideId,
                           @CookieValue(value = "loginName", defaultValue = "") String loginName) throws IOException {

        String loginId = globalControllerAdvice.getCookieValue(request, "loginId");
        Long memberId = (loginId != null) ? Long.valueOf(loginId) : null;

        MemberDTO tempGuide = memberService.findById(tempGuideId);
        memberService.deleteTemp(memberId);
        tempGuideService.deleteTemp(id);


        guideService.save(tempGuide, tempGuideId);

        model.addAttribute("msg", "가이드 신청을 허가하였습니다.");
        model.addAttribute("redirectUrl", "/");
        return "alert";

    }

    @GetMapping("/refuse/{id}")
    public String refuse(@PathVariable(name = "id") Long id, Model model,
                         HttpServletRequest request,
                          @CookieValue(value = "loginName", defaultValue = "") String loginName) {

        String loginId = globalControllerAdvice.getCookieValue(request, "loginId");
        Long memberId = (loginId != null) ? Long.valueOf(loginId) : null;

        memberService.deleteTemp(memberId);
        tempGuideService.deleteTemp(memberId);

        model.addAttribute("msg", "가이드 신청을 거절 하였습니다.");
        model.addAttribute("redirectUrl", "/");
        return "alert";
    }
}
