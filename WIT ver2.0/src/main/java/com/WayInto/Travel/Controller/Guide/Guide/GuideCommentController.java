package com.WayInto.Travel.Controller.Guide.Guide;

import com.WayInto.Travel.Service.Guide.Guide.GuideCommentService;
import com.WayInto.Travel.DTO.Guide.guide.GuideCommentDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/guide_comment")
public class GuideCommentController {

    private final GuideCommentService guideCommentService;

    @GetMapping("/list/{id}")
    public ResponseEntity<List<GuideCommentDTO>> getComments(@PathVariable Long id) {
        List<GuideCommentDTO> commentDTOList = guideCommentService.findAll(id);
        if (commentDTOList != null && !commentDTOList.isEmpty()) {
            return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/save")
    public ResponseEntity save(@ModelAttribute GuideCommentDTO commentDTO) {

        System.out.println(commentDTO);
        Long saveResult = guideCommentService.save(commentDTO);

        if (saveResult != null) {
            List<GuideCommentDTO> commentDTOList =
                    guideCommentService.findAll(commentDTO.getGuidecommentTargetId());
            return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("해당 가이드가 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }
    }
}
