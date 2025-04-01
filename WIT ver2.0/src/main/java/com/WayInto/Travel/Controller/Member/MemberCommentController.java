package com.WayInto.Travel.Controller.Member;

import com.WayInto.Travel.Service.Member.MemberCommentService;
import com.WayInto.Travel.DTO.Member.MemberCommentDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member_comment")
public class MemberCommentController {
    private final MemberCommentService memberCommentService;

    @GetMapping("/list/{id}")
    public ResponseEntity<List<MemberCommentDTO>> getComments(@PathVariable Long id) {
        List<MemberCommentDTO> commentDTOList = memberCommentService.findAll(id);
        if (commentDTOList != null && !commentDTOList.isEmpty()) {
            return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/save")
    public ResponseEntity save(@ModelAttribute MemberCommentDTO commentDTO) {

        Long saveResult = memberCommentService.save(commentDTO);

        if (saveResult != null) {
            List<MemberCommentDTO> commentDTOList = memberCommentService.findAll(commentDTO.getCommentTargetId());
            return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("해당 멤버가 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }
    }
}
