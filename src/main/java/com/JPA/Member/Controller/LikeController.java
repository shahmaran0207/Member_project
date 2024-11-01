package com.JPA.Member.Controller;

import com.JPA.Member.DTO.LikeDTO;
import com.JPA.Member.Service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
}
