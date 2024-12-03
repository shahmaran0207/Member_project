package com.JPA.Member.Controller.Travel_Review;

import com.JPA.Member.DTO.Board.BoardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("travel_review")
public class Travel_Review_Controller {

    //    @GetMapping("/paging")
//    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) {
    //   Page<BoardDTO> boardList = boardService.paging(pageable);
    //  int blockLimit = 10;
    //  int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
    //  int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();
//
    //      model.addAttribute("boardList", boardList);
    //  model.addAttribute("startPage", startPage);
    //  model.addAttribute("endPage", endPage);
    //  return "/board/paging";
    //}
}
