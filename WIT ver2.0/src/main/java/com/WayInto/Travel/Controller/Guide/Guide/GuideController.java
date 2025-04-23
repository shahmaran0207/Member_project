package com.WayInto.Travel.Controller.Guide.Guide;

import com.WayInto.Travel.Controller.ControllerAdvice.GlobalControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CookieValue;
import com.WayInto.Travel.Service.Guide.Guide.GuideService;
import org.springframework.web.bind.annotation.GetMapping;
import com.WayInto.Travel.DTO.Guide.guide.GuideDTO;
import org.springframework.stereotype.Controller;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/Guide")
public class GuideController {

    private final GlobalControllerAdvice globalControllerAdvice;
    private final GuideService guideService;

    @GetMapping("/list")
    public String findAll(Model model) {
        List<GuideDTO> GuideDTOLIST = guideService.findAll();
        model.addAttribute("GuideDTOLIST", GuideDTOLIST);
        return "Guide/list";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model,
                           HttpServletRequest request,
                           @CookieValue(value = "loginName", defaultValue = "") String loginName) {

        String loginId = globalControllerAdvice.getCookieValue(request, "loginId");
        Long memberId = (loginId != null) ? Long.valueOf(loginId) : null;

        GuideDTO guideDTO = guideService.findById(id);

        model.addAttribute("guideId", guideDTO.getMemberId());
        model.addAttribute("guide", guideDTO);
        model.addAttribute("loginId", memberId);
        model.addAttribute("loginName", loginName);
        return "Guide/detail";
    }
}
