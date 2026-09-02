package com.WayInto.Travel.Controller.Travel_Review;

import com.WayInto.Travel.Controller.ControllerAdvice.GlobalControllerAdvice;
import com.WayInto.Travel.Service.Travel_Review.TravelReviewService;
import com.WayInto.Travel.DTO.Travel_Review.ReviewDTO;
import com.WayInto.Travel.Security.AuthenticatedMember;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.WayInto.Travel.Security.ResourceGuard;
import org.springframework.data.domain.Pageable;
import jakarta.servlet.http.HttpServletRequest;
import com.WayInto.Travel.Security.LoginMember;
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
    private final ResourceGuard resourceGuard;

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

    @GetMapping("/{id}")
    public String findById(@CookieValue(value = "loginId", defaultValue = "") String loginId,
            @PathVariable Long id, Model model,
                           @PageableDefault(page=1) Pageable pageable) {
        travelReviewService.updateHits(id);

        ReviewDTO reviewDTO = travelReviewService.findById(id);

        model.addAttribute("loginId", loginId);
        model.addAttribute("review", reviewDTO);
        model.addAttribute("page", pageable.getPageNumber());
        return "Travel_Review/detail";
    }

    @GetMapping("/delete/{id}")
    public String delete(@LoginMember AuthenticatedMember member, @PathVariable("id") Long id,
                         Model model) {
        // ver2는 loginId를 받고도 쓰지 않아, 로그인만 하면 남의 후기를 지울 수 있었다.
        ReviewDTO review = travelReviewService.findById(id);
        resourceGuard.requireFound(review);
        resourceGuard.requireOwnerOrAdmin(member, review.getMember_id());

        travelReviewService.delete(id);

        model.addAttribute("msg", "여행 후기가 삭제 되었습니다.");
        model.addAttribute("redirectUrl", "/Travel_Review/paging");
        return "alert";
    }
}

