package com.JPA.Member.Controller.QnA.Answer;

import com.JPA.Member.DTO.QnA.QuestionDTO;
import com.JPA.Member.Service.QnA.Question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/QnA/Answer")
@RequiredArgsConstructor
public class AnswerController {
    private final QuestionService questionService;

    @PostMapping("/write/{id}")
    public String writeAnswer(@PathVariable("id") Long id, @RequestParam("content") String content) {
        QuestionDTO questionDTO = this.questionService.findById(id);

        return "/home";
    }
}
