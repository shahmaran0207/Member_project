package com.WayInto.Travel.Controller.Travel_Review;

import com.WayInto.Travel.Controller.ControllerAdvice.GlobalControllerAdvice;
import com.WayInto.Travel.Service.Travel_Review.TravelReviewService;
import com.WayInto.Travel.DTO.Travel_Review.ReviewDTO;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.data.domain.Pageable;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import java.io.IOException;

@RequiredArgsConstructor
@Controller
@RequestMapping("/Travel_Review")
public class Travel_ReviewController {

    private final GlobalControllerAdvice globalControllerAdvice;
    private final TravelReviewService travelReviewService;

    @GetMapping("/paging")
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) {
        Page<ReviewDTO> reviewList = travelReviewService.paging(pageable);
        int blockLimit = 10;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < reviewList.getTotalPages()) ? startPage + blockLimit - 1 : reviewList.getTotalPages();

        model.addAttribute("reviewList", reviewList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "Travel_Review/paging";
    }

    @GetMapping("/save")
    public String save(Model model) {
        return "Travel_Review/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute ReviewDTO reviewDTO, HttpServletRequest request) throws IOException {

        String loginId = globalControllerAdvice.getCookieValue(request, "loginId");
        Long memberId = (loginId != null) ? Long.valueOf(loginId) : null;

        travelReviewService.save(reviewDTO, memberId);
        return "home";
    }

}
