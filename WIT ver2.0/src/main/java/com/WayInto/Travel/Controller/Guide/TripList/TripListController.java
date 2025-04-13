package com.WayInto.Travel.Controller.Guide.TripList;

import com.WayInto.Travel.Controller.ControllerAdvice.GlobalControllerAdvice;
import com.WayInto.Travel.Service.Guide.TripList.TripListService;
import com.WayInto.Travel.Service.Member.MemberTripListService;
import com.WayInto.Travel.DTO.Guide.TripList.TripListDTO;
import com.WayInto.Travel.DTO.Member.MemberTripListDTO;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.data.domain.Pageable;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import java.io.IOException;

@Controller
@RequestMapping("/TripList")
@RequiredArgsConstructor
public class TripListController {

    private final TripListService tripListService;
    private final GlobalControllerAdvice globalControllerAdvice;
    private final MemberTripListService MemberTripListService;

    @GetMapping("/save")
    public String save(@CookieValue(value = "GuideID", defaultValue = "") String GuideID,
                       Model model) {
        if (GuideID.isEmpty()) {
            model.addAttribute("msg", "가이드만 이동 가능한 페이지입니다.");
            model.addAttribute("redirectUrl", "/");
            return "alert";
        }
        return "TripList/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute TripListDTO tripListDTO,
                       @CookieValue(value = "GuideID", defaultValue = "") String GuideID) throws IOException {

        Long memberId = Long.valueOf(GuideID);

        tripListService.save(tripListDTO, memberId);
        return "home";
    }

    @GetMapping("/paging/{id}")
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model, @PathVariable Long id,
                         @CookieValue(value = "loginId", defaultValue = "") String loginId,
                         @CookieValue(value = "GuideID", defaultValue = "") String GuideID) {
        Page<TripListDTO> tripListDTOS = tripListService.paging(pageable, id);

        int blockLimit = 10;
        int currentPage = pageable.getPageNumber() + 1;
        int totalPages = Math.max(tripListDTOS.getTotalPages(), 1);
        int startPage = Math.max(((currentPage - 1) / blockLimit) * blockLimit + 1, 1);
        int endPage = Math.min(startPage + blockLimit - 1, totalPages);

        model.addAttribute("loginId", loginId);
        model.addAttribute("GuideID", GuideID);
        model.addAttribute("triplist", tripListDTOS);
        model.addAttribute("guideId", id);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "TripList/paging";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model,
                           @PageableDefault(page=1) Pageable pageable,
                           @CookieValue(value = "loginName", defaultValue = "") String loginName,
                           @CookieValue(value = "loginEmail", defaultValue = "") String loginEmail,
                           @CookieValue(value = "loginId", defaultValue = "") String loginId,
                           @CookieValue(value = "GuideID", defaultValue = "") String GuideID) {

        tripListService.updateHits(id);

        TripListDTO tripListDTO = tripListService.findById(id);
        MemberTripListDTO memberTripListDTO = MemberTripListService.findByMemberId(id);

        model.addAttribute("loginId", loginId);
        model.addAttribute("loginName", loginName);
        model.addAttribute("GuideID", GuideID);
        model.addAttribute("loginEmail", loginEmail);
        model.addAttribute("membertrip", memberTripListDTO);
        model.addAttribute("triplist", tripListDTO);
        model.addAttribute("page", pageable.getPageNumber());
        return "TripList/detail";
    }

    @PostMapping("/payment")
    public String updateDeliveryInfo(@RequestBody TripListDTO tripListDTO, HttpServletRequest request) throws IOException {

        String loginId = globalControllerAdvice.getCookieValue(request, "loginId");
        Long memberId = (loginId != null) ? Long.valueOf(loginId) : null;

        Long TripListId = tripListDTO.getId();

        MemberTripListService.save(tripListDTO, memberId, TripListId);

        return "redirect:/Member/myPage";
    }
}
