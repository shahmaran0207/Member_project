package com.JPA.Member.Controller.QnA.Question;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.JPA.Member.Service.QnA.Question.QuestionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import com.JPA.Member.DTO.QnA.QuestionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/QnA/Question")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;


    @GetMapping("/{id}")
    public String QuestionDetail(@PathVariable Long id, Model model,
                                 @PageableDefault(page=1) Pageable pageable) {
        questionService.updateHits(id);
        QuestionDTO questionDTO = questionService.findById(id);
        model.addAttribute("question", questionDTO);
        model.addAttribute("page", pageable.getPageNumber());
        return "/QnA/Question/detail";
    }

    @GetMapping("/list")
    public String QnAList(@PageableDefault(page = 1) Pageable pageable, Model model) {
        Page<QuestionDTO> questionDTOLists = questionService.paging(pageable);
        int blockLimit = 10;
        int startPage = ((pageable.getPageNumber() / blockLimit) * blockLimit) + 1;
        int endPage = Math.min(startPage + blockLimit - 1, questionDTOLists.getTotalPages());

        model.addAttribute("questionEntityList", questionDTOLists);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "/QnA/Question/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        questionService.delete(id);
        return "redirect:/Question/QnA/list";
    }
}