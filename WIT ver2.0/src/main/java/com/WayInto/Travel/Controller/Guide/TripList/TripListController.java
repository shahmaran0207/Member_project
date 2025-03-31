package com.WayInto.Travel.Controller.Guide.TripList;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/TripList")
@RequiredArgsConstructor
public class TripListController {

    @GetMapping("/save")
    public String save(@CookieValue(value = "GuideID", defaultValue = "") String GuideID,
                       Model model) {
        if (GuideID.isEmpty()) {
            model.addAttribute("msg", "가이드만 이동 가능한 페이지입니다.");
            model.addAttribute("redirectUrl", "/"); // 홈페이지 경로
            return "alert"; // 알림 페이지로 이동
        }
        return "TripList/save"; // GuideID가 있을 경우 정상 페이지로 이동
    }


}
