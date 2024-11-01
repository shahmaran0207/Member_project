package com.JPA.Member.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.JPA.Member.Service.CommentService;
import org.springframework.http.HttpStatus;
import lombok.RequiredArgsConstructor;
import com.JPA.Member.DTO.CommentDTO;
import java.util.List;

//생성 순서: Controller-> Entity(테이블 생성) -> DTO(테이블을 서버에 전달) -> Repository(extends JPA)-> Service(Repository)
//호출 순서: Controller -> Service-> Repository -> Entity

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/save")
    public ResponseEntity save(@ModelAttribute CommentDTO commentDTO, HttpSession session) {
        Long saveResult = commentService.save(commentDTO);
        if (saveResult != null) {
            List<CommentDTO> commentDTOList = commentService.findAll(commentDTO.getBoardId());
            return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }
    }

    // 댓글 목록을 반환하는 GET 메서드 추가
    @GetMapping("/list/{id}")
    public ResponseEntity<List<CommentDTO>> getComments(@PathVariable Long id) {
        List<CommentDTO> commentDTOList = commentService.findAll(id);
        if (commentDTOList != null && !commentDTOList.isEmpty()) {
            return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}