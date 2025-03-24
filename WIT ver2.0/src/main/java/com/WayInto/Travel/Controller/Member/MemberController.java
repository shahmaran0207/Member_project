package com.WayInto.Travel.Controller.Member;

import com.WayInto.Travel.Controller.ControllerAdvice.GlobalControllerAdvice;
import com.WayInto.Travel.Service.Member.MemberService;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.data.domain.Pageable;
import com.WayInto.Travel.DTO.Member.MemberDTO;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.data.domain.Page;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import jakarta.servlet.http.Cookie;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/Member")
public class MemberController {

    private final GlobalControllerAdvice globalControllerAdvice;
    private final MemberService memberService;

    @GetMapping("/save")
    public String save() {
        return "Member/save";
    }

    @PostMapping("/email-check")
    public @ResponseBody String emailCheck(@RequestParam("memberEmail") String memberEmail) {
        String checkResult = memberService.emailCheck(memberEmail);
        if (checkResult != null) return "no";
        else return "ok";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberdto) throws IOException, FirebaseAuthException {
        memberService.save(memberdto);
        return "home";
    }

    @GetMapping("/")
    public String home(HttpServletRequest request, Model model) {
        String loginId = globalControllerAdvice.getCookieValue(request, "loginId");
        model.addAttribute("isLoggedIn", loginId != null);

        return "home";
    }

    private void setHttpOnlyCookie(HttpServletResponse response, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(3600);

        response.addCookie(cookie);
    }

    @GetMapping("/login")
    public String login() {
        return "Member/login";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> request, HttpServletRequest httpRequest,
                                        HttpServletResponse httpResponse) {
        String idToken = request.get("idToken");
        try {
            String existingLoginId = globalControllerAdvice.getCookieValue(httpRequest, "loginId");
            if (existingLoginId != null) {
                return ResponseEntity.ok("/");
            }

            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String email = decodedToken.getEmail();
            String firebaseUid = decodedToken.getUid();

            MemberDTO memberDTO = memberService.login(email);

            setHttpOnlyCookie(httpResponse, "loginId", memberDTO.getId().toString());
            setHttpOnlyCookie(httpResponse, "memberRole", String.valueOf(memberDTO.getRole()));
            setHttpOnlyCookie(httpResponse, "loginEmail", memberDTO.getMemberEmail());
            setHttpOnlyCookie(httpResponse, "loginName", memberDTO.getMemberName());
            setHttpOnlyCookie(httpResponse, "firebaseUid", firebaseUid);

            return ResponseEntity.ok("/"); // 로그인 성공
        } catch (Exception e) {
            return ResponseEntity.status(401).body("/Member/login"); // 로그인 실패
        }

    }

    @GetMapping("/myPage")
    public String myPage(Model model, HttpServletRequest request) {
        String loginId = globalControllerAdvice.getCookieValue(request, "loginId");
        Long memberId = (loginId != null) ? Long.valueOf(loginId) : null;

        if (memberId != null) {
            model.addAttribute("member", memberService.findById(memberId));
            return "Member/myPage";

        } else return "Member/login";
    }

//    @GetMapping("/Guide/{id}")
//    public String guide(HttpSession session, Model model) throws IOException {
//        Long id = (Long) session.getAttribute("loginId");
//        MemberDTO memberDTO = memberService.findById(id);
//        guideService.temp_save(memberDTO, id);
//        return "redirect:/Guide/temp_list";
//    }

//    @GetMapping("/list")
//    public String findAll(Model model) {
//        List<MemberDTO> memberDTOList = ms.findAll();
//        model.addAttribute("memberDTOList", memberDTOList);
//        return "member/list";
//    }


















//
//    @GetMapping("/save")
//    public String save() {
//        return "member/save";
//    }
//
//
//

//
////    @PostMapping("/save")
////    public String save(@ModelAttribute MemberDTO memberdto) throws IOException, FirebaseAuthException {
////        ms.save(memberdto);
////        return "home";
////    }
//
//    @GetMapping("/{id}")
//    public String findById(@PathVariable Long id, Model model) {
//        MemberDTO memberDTO = ms.findById(id);
//        model.addAttribute("member", memberDTO);
//        return "member/detail";
//    }
//
//    @GetMapping("/update/{id}")
//    public String update(@PathVariable Long id, Model model) {
//        MemberDTO memberDTO = ms.findById(id);
//        model.addAttribute("updateMember", memberDTO);
//        return "member/update";
//    }
//
//
//    @GetMapping("/myPage")
//    public String myPage(HttpSession session, Model model) {
//        Long memberId = (Long) session.getAttribute("loginId");
//
//        if (memberId != null) {
//            model.addAttribute("member", ms.findById(memberId));
//            return "member/myPage";
//
//        } else return "member/login";
//    }
//
//
//    @PostMapping("/update")
//    public String update(@ModelAttribute MemberDTO memberdto) {
//        ms.update(memberdto);
//
//        return "redirect:/member/myPage";
//    }
//
//    @GetMapping("/logout")
//    public String logout(HttpSession session) {
//
//        session.removeAttribute("loginId");
//        session.removeAttribute("loginEmail");
//        session.removeAttribute("loginEmail");
//        session.removeAttribute("memberRole");
//        session.removeAttribute("loginName");
//        session.removeAttribute("firebaseUid");
//        session.removeAttribute("GuideID");
//        return "redirect:/";
//    }
//
//    @PostMapping("/email-check")
//    public @ResponseBody String emailCheck(@RequestParam("memberEmail") String memberEmail) {
//        String checkResult = ms.emailCheck(memberEmail);
//        if (checkResult != null) return "no";
//        else return "ok";
//    }
//
//    @GetMapping("/buylist/{id}")
//    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model, HttpSession session) {
//        Long MemberId = (Long) session.getAttribute("loginId");
//        Page<MemberTripListDTO> tripListDTOS = memberTripListService.paging(pageable, MemberId);
//
//        int blockLimit = 10;
//        int currentPage = pageable.getPageNumber() + 1;
//        int totalPages = Math.max(tripListDTOS.getTotalPages(), 1);
//         int startPage = Math.max(((currentPage - 1) / blockLimit) * blockLimit + 1, 1);
//        int endPage = Math.min(startPage + blockLimit - 1, totalPages);
//
//        model.addAttribute("triplist", tripListDTOS);
//        model.addAttribute("startPage", startPage);
//        model.addAttribute("endPage", endPage);
//
//        return "member/buylist";
//    }
//
//    @GetMapping("/buydetail/{id}")
//    public String findById(@PathVariable Long id, Model model,
//                           @PageableDefault(page=1) Pageable pageable) {
//
//        MemberTripListDTO memberTripListDTO = memberTripListService.findById(id);
//
//        model.addAttribute("triplist", memberTripListDTO);
//        model.addAttribute("page", pageable.getPageNumber());
//        return "member/buydetail";
//    }
}