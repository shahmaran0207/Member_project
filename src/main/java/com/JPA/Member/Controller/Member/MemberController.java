package com.JPA.Member.Controller.Member;

import com.JPA.Member.Service.Member.MemberTripListService;
import com.JPA.Member.Service.Guide.Guide.GuideService;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.data.web.PageableDefault;
import com.JPA.Member.Service.Member.MemberService;
import com.JPA.Member.DTO.Member.MemberTripListDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.data.domain.Pageable;
import com.JPA.Member.DTO.Guide.guide.GuideDTO;
import org.springframework.http.ResponseEntity;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.data.domain.Page;
import com.JPA.Member.DTO.Member.MemberDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService ms;
    private final GuideService gs;
    private final MemberTripListService memberTripListService;

    @GetMapping("/guide/{id}")
    public String guide(HttpSession session, Model model) throws IOException {
        Long id = (Long) session.getAttribute("loginId");
        MemberDTO memberDTO = ms.findById(id);
        gs.save(memberDTO, id);
        return "redirect:/guide/list";
    }

    @GetMapping("/save")
    public String save() {
        return "member/save";
    }

    @GetMapping("/login")
    public String login() {
        return "member/login";
    }

    @GetMapping("/list")
    public String findAll(Model model) {
        List<MemberDTO> memberDTOList = ms.findAll();
        model.addAttribute("memberDTOList", memberDTOList);
        return "member/list";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberdto) throws IOException, FirebaseAuthException {
        ms.save(memberdto);
        return "home";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        MemberDTO memberDTO = ms.findById(id);
        model.addAttribute("member", memberDTO);
        return "member/detail";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        MemberDTO memberDTO = ms.findById(id);
        model.addAttribute("updateMember", memberDTO);
        return "member/update";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        String firebaseUid = (String) session.getAttribute("firebaseUid");

        try {
            if (firebaseUid != null) {
                FirebaseAuth.getInstance().deleteUser(firebaseUid);
            }
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        }

        session.removeAttribute("loginId");
        session.removeAttribute("memberRole");
        session.removeAttribute("loginEmail");
        session.removeAttribute("loginEmail");
        session.removeAttribute("loginName");
        session.removeAttribute("firebaseUid");
        session.removeAttribute("GuideID");
        ms.deleteById(id);

        return "redirect:/";
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> request, HttpSession session) {
        String idToken = request.get("idToken");
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);

            String email = decodedToken.getEmail();
            String firebaseUid = decodedToken.getUid();

            MemberDTO memberDTO = ms.login(email);

            GuideDTO guideDTO = gs.findByMemberId(memberDTO.getId());

            if(guideDTO != null) session.setAttribute("GuideID", guideDTO.getId());

            session.setAttribute("loginId", memberDTO.getId());
            session.setAttribute("memberRole", memberDTO.getRole());
            session.setAttribute("loginEmail", memberDTO.getMemberEmail());
            session.setAttribute("loginName", memberDTO.getMemberName());
            session.setAttribute("firebaseUid", firebaseUid);

            return ResponseEntity.ok("/");
        } catch (Exception e) {
            return ResponseEntity.status(401).body("member/login");
        }
    }


    @GetMapping("/myPage")
    public String myPage(HttpSession session, Model model) {
        Long memberId = (Long) session.getAttribute("loginId");

        if (memberId != null) {
            model.addAttribute("member", ms.findById(memberId));
            return "member/myPage";

        } else return "member/login";
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
        session.removeAttribute("loginEmail");
        session.removeAttribute("memberRole");
        session.removeAttribute("loginName");
        session.removeAttribute("firebaseUid");
        session.removeAttribute("GuideID");
        return "redirect:/";
    }

    @PostMapping("/email-check")
    public @ResponseBody String emailCheck(@RequestParam("memberEmail") String memberEmail) {
        String checkResult = ms.emailCheck(memberEmail);
        if (checkResult != null) return "no";
        else return "ok";
    }

    @GetMapping("/buylist/{id}")
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model, HttpSession session) {
        Long MemberId = (Long) session.getAttribute("loginId");
        Page<MemberTripListDTO> tripListDTOS = memberTripListService.paging(pageable, MemberId);

        int blockLimit = 10;
        int currentPage = pageable.getPageNumber() + 1;
        int totalPages = Math.max(tripListDTOS.getTotalPages(), 1);
         int startPage = Math.max(((currentPage - 1) / blockLimit) * blockLimit + 1, 1);
        int endPage = Math.min(startPage + blockLimit - 1, totalPages);

        model.addAttribute("triplist", tripListDTOS);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "member/buylist";
    }

    @GetMapping("/buydetail/{id}")
    public String findById(@PathVariable Long id, Model model,
                           @PageableDefault(page=1) Pageable pageable) {

        MemberTripListDTO memberTripListDTO = memberTripListService.findById(id);

        model.addAttribute("triplist", memberTripListDTO);
        model.addAttribute("page", pageable.getPageNumber());
        return "member/buydetail";
    }
}