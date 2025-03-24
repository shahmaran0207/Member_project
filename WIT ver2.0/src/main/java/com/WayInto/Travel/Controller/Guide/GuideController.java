package com.WayInto.Travel.Controller.Guide;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import com.WayInto.Travel.Service.Guide.GuideService;
import com.WayInto.Travel.DTO.Guide.guide.GuideDTO;
import org.springframework.stereotype.Controller;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/Guide")
public class GuideController {
    private final GuideService guideService;

    @GetMapping("/list")
    public String findAll(Model model) {
        List<GuideDTO> GuideDTOLIST = guideService.findAll();
        model.addAttribute("GuideDTOLIST", GuideDTOLIST);
        return "Guide/list";
    }
}
