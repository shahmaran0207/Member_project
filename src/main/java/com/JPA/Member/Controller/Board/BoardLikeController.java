package com.JPA.Member.Controller.Board;

import org.springframework.web.bind.annotation.*;
import com.JPA.Member.Service.Board.BoardLikeService;
import com.JPA.Member.DTO.Board.BoardLikeDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class BoardLikeController {

    private final BoardLikeService boardLikeService;

    @PostMapping("/toggle")
    public String toggleLike(@RequestBody BoardLikeDTO boardLikeDTO) {
        return boardLikeService.toggleLike(boardLikeDTO);
    }

    @GetMapping("/count/{boardId}")
    public int getLikeCount(@PathVariable Long boardId) {
        return boardLikeService.getLikeCount(boardId);
    }

    @GetMapping("/status/{boardId}/{memberId}")
    public boolean checkLikeStatus(@PathVariable Long boardId, @PathVariable Long memberId) {
        return boardLikeService.isLikedByMember(boardId, memberId);
    }

}

