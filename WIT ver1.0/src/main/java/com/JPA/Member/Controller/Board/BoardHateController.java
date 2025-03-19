package com.JPA.Member.Controller.Board;

import com.JPA.Member.Service.Board.BoardHateService;
import org.springframework.web.bind.annotation.*;
import com.JPA.Member.DTO.Board.BoardHateDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/boardhates")
@RequiredArgsConstructor
public class BoardHateController {

    private final BoardHateService boardHateService;

    @PostMapping("/toggle")
    public String toggleHate(@RequestBody BoardHateDTO boardHateDTO) {
        return boardHateService.toggleHate(boardHateDTO);
    }

    @GetMapping("/count/{boardId}")
    public int getHateCount(@PathVariable Long boardId) {
        return boardHateService.getHateCount(boardId);
    }

    @GetMapping("/status/{boardId}/{memberId}")
    public boolean checkHateStatus(@PathVariable Long boardId, @PathVariable Long memberId) {
        return boardHateService.isHatedByMember(boardId, memberId);
    }

}

