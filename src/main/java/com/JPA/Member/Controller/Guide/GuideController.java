package com.JPA.Member.Controller.Guide;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import com.JPA.Member.Service.Member.MemberService;
import org.springframework.stereotype.Controller;
import com.JPA.Member.Service.Guide.GuideService;
import com.JPA.Member.DTO.Member.MemberDTO;
import com.JPA.Member.DTO.Guide.GuideDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/guide")
public class GuideController {
    private final GuideService guideService;
    private final MemberService memberService;

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

    @GetMapping("/save")
    public String save(Model model, HttpSession session) {
        Long id = (Long) session.getAttribute("loginId");
        MemberDTO memberDTO = memberService.findById(id);

        model.addAttribute("member", memberDTO);
        return "/guide/save";
    }

    @GetMapping("/guide/{id}")
    public String guide(HttpSession session, Model model) throws IOException {
        Long id = (Long) session.getAttribute("loginId");
        MemberDTO memberDTO = memberService.findById(id);

        guideService.save(memberDTO, id);
        return "redirect:/";
    }
}
