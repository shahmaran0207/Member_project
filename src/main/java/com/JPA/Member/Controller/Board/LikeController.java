package com.JPA.Member.Controller.Board;

import org.springframework.web.bind.annotation.*;
import com.JPA.Member.Service.Board.LikeService;
import lombok.RequiredArgsConstructor;
import com.JPA.Member.DTO.Board.LikeDTO;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/toggle")
    public String toggleLike(@RequestBody LikeDTO likeDTO) {
        return likeService.toggleLike(likeDTO);
    }

    @GetMapping("/count/{boardId}")
    public int getLikeCount(@PathVariable Long boardId) {
        return likeService.getLikeCount(boardId);
    }

    @GetMapping("/status/{boardId}/{memberId}")
    public boolean checkLikeStatus(@PathVariable Long boardId, @PathVariable Long memberId) {
        return likeService.isLikedByMember(boardId, memberId);
    }

}

