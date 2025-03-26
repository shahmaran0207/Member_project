package com.WayInto.Travel.Controller.Board;

import com.WayInto.Travel.Controller.ControllerAdvice.GlobalControllerAdvice;
import com.WayInto.Travel.Service.Board.CommentService;
import com.WayInto.Travel.Service.Board.BoardService;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import com.WayInto.Travel.DTO.Board.CommentDTO;
import jakarta.servlet.http.HttpServletRequest;
import com.WayInto.Travel.DTO.Board.BoardDTO;
import org.springframework.data.domain.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/Board")
public class BoardController {

    private final GlobalControllerAdvice globalControllerAdvice;
    private final CommentService commentService;
    private final BoardService boardService;

    @GetMapping("/paging")
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) {
        Page<BoardDTO> boardList = boardService.paging(pageable);
        int blockLimit = 10;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();

        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "Board/paging";
    }

    @GetMapping("/save")
    public String save() {
        return "Board/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO, HttpServletRequest request) throws IOException {
        String loginId = globalControllerAdvice.getCookieValue(request, "loginId");
        Long id = (loginId != null) ? Long.valueOf(loginId) : null;
        boardService.save(boardDTO, id);
        return "home";
    }

    @GetMapping("/{id}")
    public String findById(@CookieValue(value = "loginId", defaultValue = "") String loginId,
                           @CookieValue(value = "loginName", defaultValue = "") String loginName,
                           @PathVariable("id") Long id, Model model, @PageableDefault(page=1) Pageable pageable) {
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("loginId", loginId);
        model.addAttribute("loginName", loginName);
        List<CommentDTO> commentDTOList = commentService.findAll(id);
        model.addAttribute("commentList", commentDTOList);
        model.addAttribute("board", boardDTO);
        model.addAttribute("page", pageable.getPageNumber());

        return "Board/detail";
    }

    @GetMapping("/delete/{id}")
    public String delete(@CookieValue(value = "loginId", defaultValue = "") String loginId, @PathVariable("id") Long id) {
        boardService.delete(id);
        return "redirect:/Board/paging";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@CookieValue(value = "loginId", defaultValue = "") String loginId, @PathVariable Long id, Model model) {
        model.addAttribute("loginId", loginId);
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardUpdate", boardDTO);
        return "Board/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO, HttpServletRequest request,
                         Model model) throws IOException {
        String loginId = globalControllerAdvice.getCookieValue(request, "loginId");
        Long memberId = (loginId != null) ? Long.valueOf(loginId) : null;
        BoardDTO board = boardService.update(boardDTO, memberId);

        model.addAttribute("loginId", loginId);
        model.addAttribute("board", board);
        return "Board/detail";
    }
}
