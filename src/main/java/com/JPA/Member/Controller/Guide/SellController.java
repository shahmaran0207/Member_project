package com.JPA.Member.Controller.Guide;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.JPA.Member.Service.Guide.TripList.TripListService;
import org.springframework.web.bind.annotation.GetMapping;
import com.JPA.Member.DTO.Guide.TripList.TripListDTO;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;

@Controller
@RequiredArgsConstructor
@RequestMapping("/TripList/")
public class SellController {
    private final TripListService tripListService;

    @GetMapping("/paging/{id}")
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model, @PathVariable Long id) {
        Page<TripListDTO> tripListDTOS = tripListService.paging(pageable, id);

        int blockLimit = 10; // 페이지네이션 블록 단위
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


}
