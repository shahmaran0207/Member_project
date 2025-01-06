package com.JPA.Member.Controller.Guide;

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
@RequestMapping("/TripList")
public class SellController {
    private final TripListService tripListService;

    @GetMapping("/paging")
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model, Long GuideId) {
        Page<TripListDTO> tripListDTOS = tripListService.paging(pageable ,GuideId);
        int blockLimit = 10;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < tripListDTOS.getTotalPages()) ? startPage + blockLimit - 1 : tripListDTOS.getTotalPages();

        model.addAttribute("triplist", tripListDTOS);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "/TripList/paging";
    }
}
