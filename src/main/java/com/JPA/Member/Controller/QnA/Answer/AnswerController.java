package com.JPA.Member.Controller.QnA.Answer;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import com.JPA.Member.Service.QnA.Question.QuestionService;
import com.JPA.Member.Entity.QnA.Question.QuestionEntity;
import com.JPA.Member.Service.QnA.Answer.AnswerService;
import com.JPA.Member.DTO.QnA.Question.QuestionDTO;
import org.springframework.stereotype.Controller;
import lombok.RequiredArgsConstructor;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/QnA/Answer")
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;

        @PostMapping("/write/{id}")
        public String writeAnswer(@PathVariable("id") Long id, @RequestBody Map<String, Object> requestData) throws IOException {
            Integer memberId = (Integer) requestData.get("memberId");
            String commentContents = (String) requestData.get("commentContents");
            Long questionid = Long.valueOf(requestData.get("Question").toString());
            QuestionDTO questionDTO = questionService.findById(questionid);
            QuestionEntity question = questionDTO.toEntity(questionDTO);
            Long member = memberId.longValue();
            answerService.save(member, commentContents, question);

            return "/home";
        }
    }
