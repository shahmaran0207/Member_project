package com.WayInto.Travel.Controller.Guide.TripList;

import com.WayInto.Travel.Service.Guide.TripList.TripListService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import com.WayInto.Travel.DTO.Guide.TripList.TripListDTO;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/TripList")
@RequiredArgsConstructor
public class TripListController {

    private final TripListService tripListService;

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

}
