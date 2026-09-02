package com.WayInto.Travel.Controller.Board;

import com.WayInto.Travel.Controller.ControllerAdvice.GlobalControllerAdvice;
import com.WayInto.Travel.Service.Board.CommentService;
import com.WayInto.Travel.Security.AuthenticatedMember;
import com.WayInto.Travel.Service.Board.BoardService;
import org.springframework.data.web.PageableDefault;
import com.WayInto.Travel.Security.ResourceGuard;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import com.WayInto.Travel.Security.LoginMember;
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
    private final ResourceGuard resourceGuard;

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
                           @PathVariable("id") Long id, Model model, @PageableDefault(page=1) Pageable pageable,
                           @CookieValue(value = "memberRole", defaultValue = "") String memberRole) {
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("loginId", loginId);
        model.addAttribute("loginName", loginName);
        List<CommentDTO> commentDTOList = commentService.findAll(id);
        model.addAttribute("memberRole", memberRole);
        model.addAttribute("commentList", commentDTOList);
        model.addAttribute("board", boardDTO);
        model.addAttribute("page", pageable.getPageNumber());

        return "Board/detail";
    }

    @GetMapping("/delete/{id}")
    public String delete(@LoginMember AuthenticatedMember member, @PathVariable("id") Long id) {
        // ver2는 loginId를 받고도 쓰지 않아, 로그인만 하면 남의 글을 지울 수 있었다.
        BoardDTO board = boardService.findById(id);
        resourceGuard.requireFound(board);
        resourceGuard.requireOwnerOrAdmin(member, board.getMemberId());

        boardService.delete(id);
        return "redirect:/Board/paging";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@LoginMember AuthenticatedMember member, @PathVariable Long id, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        resourceGuard.requireFound(boardDTO);
        resourceGuard.requireOwnerOrAdmin(member, boardDTO.getMemberId());

        model.addAttribute("loginId", member.id());
        model.addAttribute("boardUpdate", boardDTO);
        return "Board/update";
    }

    @PostMapping("/update")
    public String update(@LoginMember AuthenticatedMember member,
                         @ModelAttribute BoardDTO boardDTO, Model model) throws IOException {
        // 폼으로 넘어온 id를 그대로 믿으면 남의 글을 수정할 수 있다.
        // BoardService.update가 작성자를 요청자로 덮어쓰기 때문에 소유권까지 넘어간다.
        BoardDTO existing = boardService.findById(boardDTO.getId());
        resourceGuard.requireFound(existing);
        resourceGuard.requireOwnerOrAdmin(member, existing.getMemberId());

        // 원래 작성자를 유지한다. 관리자가 수정해도 소유권은 옮겨가지 않는다.
        BoardDTO board = boardService.update(boardDTO, existing.getMemberId());

        model.addAttribute("loginId", member.id());
        model.addAttribute("board", board);
        return "Board/detail";
    }

}
