package com.WayInto.Travel.Controller.Member;

import com.WayInto.Travel.Controller.ControllerAdvice.GlobalControllerAdvice;
import com.WayInto.Travel.Service.Guide.Guide.Temp_GuideService;
import com.WayInto.Travel.Service.Member.MemberTripListService;
import com.WayInto.Travel.Service.Guide.Guide.GuideService;
import com.WayInto.Travel.Security.AuthenticatedMemberLoader;
import com.WayInto.Travel.Security.FirebaseSessionService;
import com.WayInto.Travel.Security.AuthenticatedMember;
import com.WayInto.Travel.Service.Member.MemberService;
import com.WayInto.Travel.DTO.Member.MemberTripListDTO;
import com.WayInto.Travel.Security.LoginMember;
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
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import java.io.IOException;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/Member")
public class MemberController {

    private final GlobalControllerAdvice globalControllerAdvice;
    private final MemberService memberService;
    private final Temp_GuideService tempGuideService;
    private final MemberTripListService memberTripListService;
    private final GuideService guideService;
    private final FirebaseSessionService sessionService;
    private final AuthenticatedMemberLoader memberLoader;

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

    @GetMapping("/login")
    public String login() {
        return "Member/login";
    }

    /**
     * Firebase idToken을 검증하고 서명된 세션 쿠키를 발급한다.
     *
     * ver2는 loginId 쿠키가 이미 있으면 토큰 검증을 건너뛰고 성공을 반환했다.
     * 위조 쿠키 하나로 인증을 통째로 우회할 수 있었기 때문에 그 분기를 제거했다.
     *
     * 신원 정보(loginId, memberRole 등)는 더 이상 브라우저로 내려보내지 않는다.
     * 요청마다 서버가 세션 쿠키를 검증해 다시 만든다.
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> request,
                                        HttpServletResponse httpResponse) {
        String idToken = request.get("idToken");
        if (idToken == null || idToken.isBlank()) {
            return ResponseEntity.status(401).body("/Member/login");
        }

        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken, true);
            String email = decodedToken.getEmail();

            MemberDTO memberDTO = memberService.login(email);
            if (memberDTO == null || memberDTO.getId() == null) {
                return ResponseEntity.status(401).body("/Member/login");
            }

            String sessionCookie = sessionService.createSessionCookie(idToken);
            sessionService.writeSessionCookie(httpResponse, sessionCookie);

            // 로그인 직전의 낡은 캐시가 남아 있으면 비운다.
            memberLoader.evict(decodedToken.getUid());

            return ResponseEntity.ok("/");
        } catch (Exception e) {
            // 실패 사유를 구분해 돌려주지 않는다. 계정 존재 여부를 알려주는 통로가 되기 때문이다.
            return ResponseEntity.status(401).body("/Member/login");
        }
    }

    @GetMapping("/logout")
    public String logout(@LoginMember(required = false) AuthenticatedMember member,
                         HttpServletResponse response) {
        if (member != null) {
            sessionService.revoke(member.firebaseUid());
            memberLoader.evict(member.firebaseUid());
        }
        sessionService.clearSessionCookie(response);
        return "redirect:/";
    }

    @GetMapping("/myPage")
    public String myPage(Model model, HttpServletRequest request, @CookieValue(value = "memberRole", defaultValue = "") String memberRole,
                         @CookieValue(value = "GuideID", defaultValue = "") String GuideID,
                         @CookieValue(value = "tempGuide", defaultValue = "") String tempGuide) {

        String loginId = globalControllerAdvice.getCookieValue(request, "loginId");
        Long memberId = (loginId != null) ? Long.valueOf(loginId) : null;

        model.addAttribute("memberRole", memberRole);
        model.addAttribute("GuideID", GuideID);
        model.addAttribute("tempGuide", tempGuide);

        if (memberId != null) {
            model.addAttribute("member", memberService.findById(memberId));
            return "Member/myPage";

        } else return "Member/login";
    }

    @GetMapping("/paging")
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model,
                         @RequestParam(value = "area", required = false) String area) {
        Page<MemberDTO> memberList = memberService.paging(pageable, area);
        int blockLimit = 10;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < memberList.getTotalPages()) ? startPage + blockLimit - 1 : memberList.getTotalPages();

        model.addAttribute("memberList", memberList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("area", area);
        return "Member/paging";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model, HttpServletRequest request,
                           @CookieValue(value = "GuideID", defaultValue = "") String GuideID,
                           @CookieValue(value = "loginName", defaultValue = "") String loginName) {

        String loginId = globalControllerAdvice.getCookieValue(request, "loginId");
        Long memberId = (loginId != null) ? Long.valueOf(loginId) : null;

        model.addAttribute("GuideID", GuideID);
        model.addAttribute("loginId", memberId);
        model.addAttribute("loginName", loginName);
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "Member/detail";
    }

    @GetMapping("/Guide/{id}")
    public String guide(HttpServletRequest request, Model model) throws IOException {
        String loginId = globalControllerAdvice.getCookieValue(request, "loginId");
        Long memberId = (loginId != null) ? Long.valueOf(loginId) : null;

        MemberDTO memberDTO = memberService.findById(memberId);
        memberService.saveTempGuide(memberId);
        tempGuideService.save(memberDTO, memberId);
        model.addAttribute("msg", "가이드 신청이 완료되었습니다.");
        model.addAttribute("redirectUrl", "/");
        return "alert";
    }

    @GetMapping("/attendance_check")
    public String Attend_Check(Model model,
                               @CookieValue(value = "loginId", defaultValue = "") String loginId) {

        model.addAttribute("loginId", loginId);
        return "Member/attendance_check";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@CookieValue(value = "loginId", defaultValue = "") String loginId,
                             @PathVariable("id") Long id,
                             Model model) {
        model.addAttribute("loginId", loginId);
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("updateMember", memberDTO);
        return "Member/update";
    }

    @PostMapping("/update")
    public String update(@CookieValue(value = "firebaseUid", defaultValue = "") String firebaseUid,
                         @ModelAttribute MemberDTO memberDTO, HttpServletRequest request,
                         Model model) throws IOException, FirebaseAuthException {
        try {
            if (firebaseUid != null) {
                FirebaseAuth.getInstance().deleteUser(firebaseUid);
            }
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        }
        memberService.update(memberDTO);

        model.addAttribute("member", memberDTO);
        return "redirect:/";
    }

    @GetMapping("/buylist/{id}")
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model, HttpServletRequest request) {

        String loginId = globalControllerAdvice.getCookieValue(request, "loginId");
        Long memberId = (loginId != null) ? Long.valueOf(loginId) : null;

        Page<MemberTripListDTO> tripListDTOS = memberTripListService.paging(pageable, memberId);

        int blockLimit = 10;
        int currentPage = pageable.getPageNumber() + 1;
        int totalPages = Math.max(tripListDTOS.getTotalPages(), 1);
         int startPage = Math.max(((currentPage - 1) / blockLimit) * blockLimit + 1, 1);
        int endPage = Math.min(startPage + blockLimit - 1, totalPages);

        model.addAttribute("triplist", tripListDTOS);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "Member/buylist";
    }

    @GetMapping("/buydetail/{id}")
    public String findById(@PathVariable Long id, Model model, HttpServletRequest request,
                           @PageableDefault(page=1) Pageable pageable) {

        String loginId = globalControllerAdvice.getCookieValue(request, "loginId");
        Long memberId = (loginId != null) ? Long.valueOf(loginId) : null;

        MemberTripListDTO memberTripListDTO = memberTripListService.findById(id);

        model.addAttribute("memberId", memberId);
        model.addAttribute("triplist", memberTripListDTO);
        model.addAttribute("page", pageable.getPageNumber());
        return "Member/buydetail";
    }
}