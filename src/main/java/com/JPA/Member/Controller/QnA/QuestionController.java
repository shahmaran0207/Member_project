package com.JPA.Member.Controller.QnA;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import com.JPA.Member.Service.QnA.Question.QuestionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import com.JPA.Member.Entity.QnA.QuestionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import java.util.List;

@Controller
@RequestMapping("/QnA/Question")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/board")
    @ResponseBody
    public String QnABoard() {
        return "/QnA/Question/board";
    }

    @GetMapping("/list")
    public String QnAList(Model model) {
        List<QuestionEntity> questionEntityList = questionService.findAll();

        model.addAttribute("questionEntityList", questionEntityList);

        return "/QnA/Question/list";
    }

    @GetMapping("/detail")
    public String QuestionDetail(Model model, @PathVariable("id") Integer id) {
        return "/QnA/Question/detail";
    }
}