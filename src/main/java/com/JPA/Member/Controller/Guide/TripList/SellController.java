package com.JPA.Member.Controller.Guide.TripList;

import com.JPA.Member.Service.Guide.TripList.TripListService;
import com.JPA.Member.Service.Member.MemberTripListService;
import com.JPA.Member.DTO.Guide.TripList.TripListDTO;
import org.springframework.data.web.PageableDefault;
import com.JPA.Member.DTO.Member.MemberTripListDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/TripList")
public class SellController {
    private final TripListService tripListService;
    private final MemberTripListService memberTripListService;

    @GetMapping("/paging/{id}")
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model, @PathVariable Long id) {
        Page<TripListDTO> tripListDTOS = tripListService.paging(pageable, id);

        int blockLimit = 10;
        int currentPage = pageable.getPageNumber() + 1; // 현재 페이지 (1부터 시작)
        int totalPages = Math.max(tripListDTOS.getTotalPages(), 1); // 최소 1페이지 보장
        int startPage = Math.max(((currentPage - 1) / blockLimit) * blockLimit + 1, 1); // 최소 1부터 시작
        int endPage = Math.min(startPage + blockLimit - 1, totalPages); // 최대 페이지 초과 방지

        model.addAttribute("triplist", tripListDTOS);
        model.addAttribute("guideId", id);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "/TripList/paging";
    }

    @GetMapping("/save")
    public String save(Model model) {
        return "/TripList/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute TripListDTO tripListDTO, HttpSession session) throws IOException {
        Long GuideID = (Long) session.getAttribute("GuideID");
        tripListService.save(tripListDTO, GuideID);
        return "home";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model, HttpSession session,
                           @PageableDefault(page=1) Pageable pageable) {
        tripListService.updateHits(id);

        Long MemberId = (Long) session.getAttribute("loginId");

        TripListDTO tripListDTO = tripListService.findById(id);
        MemberTripListDTO memberTripListDTO = memberTripListService.findByMemberId(MemberId);

        model.addAttribute("membertrip", memberTripListDTO);
        model.addAttribute("triplist", tripListDTO);
        model.addAttribute("page", pageable.getPageNumber());
        return "/TripList/detail";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        tripListService.delete(id);
        return "redirect:/TripList/paging";
    }

    @PostMapping("/payment")
    public String updateDeliveryInfo(@RequestBody TripListDTO tripListDTO, HttpSession session) throws IOException {
        Long MemberId = (Long) session.getAttribute("loginId");
        Long TripListId = tripListDTO.getId();

        memberTripListService.save(tripListDTO, MemberId, TripListId);

        return "redirect:/member/myPage";
    }
}
