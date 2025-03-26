package com.WayInto.Travel.Controller.Board;

import com.WayInto.Travel.Service.Board.BoardLikeService;
import com.WayInto.Travel.DTO.Board.BoardLikeDTO;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/board_likes")
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