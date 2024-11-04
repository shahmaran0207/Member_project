package com.JPA.Member.Controller.Guide;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import com.JPA.Member.Service.Guide.GuideService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import com.JPA.Member.DTO.Guide.GuideDTO;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/guide")
public class GuideController {
    private final GuideService guideService;

    @GetMapping("/list")
    public String findAll(Model model) {
        List<GuideDTO> GuideDTOLIST = guideService.findAll();
        model.addAttribute("GuideDTOLIST", GuideDTOLIST);
        return "/guide/list";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        GuideDTO guideDTO = guideService.findById(id);
        model.addAttribute("guide", guideDTO);
        return "/guide/detail";
    }


}
