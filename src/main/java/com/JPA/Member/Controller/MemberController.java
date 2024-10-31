package com.JPA.Member.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.JPA.Member.Service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import com.JPA.Member.DTO.MemberDTO;
import org.springframework.ui.Model;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService ms;

    @GetMapping("/save")
    public String save() {
        return "/member/save";
    }

    @GetMapping("/login")
    public String login() {
        return "/member/login";
    }

    @GetMapping("/list")
    public String findAll(Model model) {
        List<MemberDTO> memberDTOList = ms.findAll();
        model.addAttribute("memberDTOList", memberDTOList);

        return "/member/list";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberdto) {

        ms.save(memberdto);

        return "/home";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberdto, HttpSession session) {
        MemberDTO loginresult = ms.login(memberdto);

        if(loginresult != null) {
            session.setAttribute("loginEmail", loginresult);

            return "/home";

        } else{
            return "/home";
        }
    }
}