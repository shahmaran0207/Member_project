package com.JPA.Member.Controller.QnA.Question;

import com.JPA.Member.Service.QnA.Question.QuestionService;
import com.JPA.Member.Service.QnA.Answer.AnswerService;
import org.springframework.data.web.PageableDefault;
import com.JPA.Member.DTO.QnA.Question.QuestionDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.data.domain.Pageable;
import com.JPA.Member.DTO.QnA.Answer.AnswerDTO;
import org.springframework.data.domain.Page;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import java.io.IOException;

@Controller
@RequestMapping("/QnA/Question")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    private final AnswerService answerService;

    @GetMapping("/{id}")
    public String QuestionDetail(@PathVariable Long id, Model model,
                                 @PageableDefault(page=1) Pageable pageable) {
        questionService.updateHits(id);
        QuestionDTO questionDTO = questionService.findById(id);
        AnswerDTO answerDTO = answerService.findByQuestionId(id);
        model.addAttribute("answer", answerDTO);
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
        return "redirect:/QnA/Question/list";
    }

    @GetMapping("/write")
    public String saveForm() {
        return "/QnA/Question/write";
    }

    @PostMapping("/write")
    public String save(@ModelAttribute QuestionDTO questionDTO, HttpSession session) throws IOException {
        Long id = (Long) session.getAttribute("loginId");
        questionService.save(questionDTO, id);
        return "home";
    }
}