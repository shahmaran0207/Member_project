package com.JPA.Member.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.JPA.Member.Service.MemberService;
import com.JPA.Member.Service.GuideService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import com.JPA.Member.DTO.MemberDTO;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService ms;
    private final GuideService gs;

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

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        MemberDTO memberDTO = ms.findById(id);
        model.addAttribute("member", memberDTO);
        return "/member/detail";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        MemberDTO memberDTO = ms.findById(id);
        model.addAttribute("updateMember", memberDTO);
        return "/member/update";
    }

    @GetMapping("/guide/{id}")
    public String guide(HttpSession session, Model model) throws IOException {
        Long id = (Long) session.getAttribute("loginId");
        MemberDTO memberDTO = ms.findById(id);

        gs.save(memberDTO, id);

        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        session.removeAttribute("loginId");
        session.removeAttribute("loginEmail");
        session.removeAttribute("loginName");
        ms.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberdto, HttpSession session) {
        MemberDTO loginresult = ms.login(memberdto);

        if (loginresult != null) {
            // 세션에 로그인한 사용자의 ID를 저장
            session.setAttribute("loginId", loginresult.getId()); // Long으로 저장
            session.setAttribute("loginEmail", loginresult.getMemberEmail());
            session.setAttribute("loginName", loginresult.getMemberName()); // 사용자 이름 저장

            return "/home";
        } else {
            return "/home"; // 로그인 실패 처리 추가 필요
        }
    }

    @GetMapping("/myPage")
    public String myPage(HttpSession session, Model model) {
        Long memberId = (Long) session.getAttribute("loginId");

        if (memberId != null) {
            model.addAttribute("member", ms.findById(memberId));
            return "/member/myPage";
        } else {
            return "/member/login";
        }
    }

    @PostMapping("/update")
    public String update(@ModelAttribute MemberDTO memberdto) {
        ms.update(memberdto);

        //redirect: 새 URL 요청을 지시 (삭제나 수정 후 같이 새로운 url로 이동해야 하는 경우 사용)
        //return: 서버 내에서 뷰 렌더링 -> 사용자에게 응답
        return "redirect:/member/myPage";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loginId");
        session.removeAttribute("loginEmail");
        session.removeAttribute("loginName");
        return "redirect:/";
    }

    @PostMapping("/email-check")
    public @ResponseBody String emailCheck(@RequestParam("memberEmail") String memberEmail) {
        String checkResult = ms.emailCheck(memberEmail);
            if (checkResult != null) {
                return "no";
            } else {
                return "ok";
            }
    }
}
