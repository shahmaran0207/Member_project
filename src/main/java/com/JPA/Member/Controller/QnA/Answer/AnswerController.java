package com.JPA.Member.Controller.QnA.Answer;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import com.JPA.Member.Service.QnA.Question.QuestionService;
import com.JPA.Member.DTO.QnA.Question.QuestionDTO;
import org.springframework.stereotype.Controller;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Controller
@RequestMapping("/QnA/Answer")
@RequiredArgsConstructor
public class AnswerController {
    private final QuestionService questionService;

        @PostMapping("/write/{id}")
        public String writeAnswer(@PathVariable("id") Long id, @RequestBody Map<String, Object> requestData) {
            // 데이터 추출
            Integer memberId = (Integer) requestData.get("memberId"); // Integer 타입으로 추출
            String commentContents = (String) requestData.get("commentContents");
            Long question = Long.valueOf(requestData.get("Question").toString()); // Long 타입으로 변환
            String answerStatus = (String) requestData.get("answerStatus");

            // 데이터 출력
            System.out.println("Member ID: " + memberId);
            System.out.println("Comment Contents: " + commentContents);
            System.out.println("Question: " + question);
            System.out.println("Answer Status: " + answerStatus);

            // QuestionDTO 가져오기
            QuestionDTO questionDTO = this.questionService.findById(id);

            // 추가 로직 구현
            return "/home";
        }
    }
