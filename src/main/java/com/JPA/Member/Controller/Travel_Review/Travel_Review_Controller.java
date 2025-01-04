package com.JPA.Member.Controller.Travel_Review;

import com.JPA.Member.Service.Travel_Review.TravelReviewService;
import org.springframework.data.web.PageableDefault;
import com.JPA.Member.DTO.Travel_Review.ReviewDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("Travel_Review")
public class Travel_Review_Controller {

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
        return "/Travel_Review/paging";
    }

    @GetMapping("/save")
    public String save(Model model) {
        return "/Travel_Review/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute ReviewDTO reviewDTO, HttpSession session) throws IOException {
        Long memberId = (Long) session.getAttribute("loginId");
        travelReviewService.save(reviewDTO, memberId);
        return "home";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model,
                           @PageableDefault(page=1) Pageable pageable) {
        travelReviewService.updateHits(id);

        ReviewDTO reviewDTO = travelReviewService.findById(id);

        model.addAttribute("review", reviewDTO);
        model.addAttribute("page", pageable.getPageNumber());
        return "/Travel_Review/detail";
    }
}