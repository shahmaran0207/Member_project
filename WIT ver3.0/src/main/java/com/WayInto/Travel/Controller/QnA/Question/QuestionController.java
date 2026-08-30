package com.WayInto.Travel.Controller.QnA.Question;

import com.WayInto.Travel.Controller.ControllerAdvice.GlobalControllerAdvice;
import com.WayInto.Travel.Service.QnA.Question.QuestionService;
import com.WayInto.Travel.Service.QnA.Answer.AnswerService;
import com.WayInto.Travel.DTO.QnA.Question.QuestionDTO;
import org.springframework.data.web.PageableDefault;
import com.WayInto.Travel.DTO.QnA.Answer.AnswerDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.data.domain.Pageable;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import java.io.IOException;

@Controller
@RequestMapping("/QnA/Question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final GlobalControllerAdvice globalControllerAdvice;

    @GetMapping("/list")
    public String QnAList(@PageableDefault(page = 1) Pageable pageable, Model model) {
        Page<QuestionDTO> questionDTOLists = questionService.paging(pageable);
        int blockLimit = 10;
        int startPage = ((pageable.getPageNumber() / blockLimit) * blockLimit) + 1;
        int endPage = Math.min(startPage + blockLimit - 1, questionDTOLists.getTotalPages());

        model.addAttribute("questionEntityList", questionDTOLists);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "QnA/Question/list";
    }

    @GetMapping("/write")
    public String QuestionSave() {
        return "QnA/Question/write";
    }

    @PostMapping("/write")
    public String save(@ModelAttribute QuestionDTO questionDTO, HttpServletRequest request)
            throws IOException {
        String loginId = globalControllerAdvice.getCookieValue(request, "loginId");
        Long id = (loginId != null) ? Long.valueOf(loginId) : null;

        questionService.save(questionDTO, id);
        return "home";
    }

    @GetMapping("/{id}")
    public String QuestionDetail(@CookieValue(value = "loginId", defaultValue = "") String loginId,
                                 @CookieValue(value = "loginName") String loginName,
                                 @PathVariable("id") Long id, Model model,
                                 @CookieValue(value = "memberRole", defaultValue = "")
                                     String memberRole,
                                 @PageableDefault(page=1) Pageable pageable) {

        questionService.updateHits(id);
        QuestionDTO questionDTO = questionService.findById(id);
        AnswerDTO answerDTO = answerService.findByQuestionId(id);

        model.addAttribute("loginId", loginId);
        model.addAttribute("loginName", loginName);
        model.addAttribute("memberRole", memberRole);
        model.addAttribute("answer", answerDTO);
        model.addAttribute("question", questionDTO);
        model.addAttribute("page", pageable.getPageNumber());
        return "QnA/Question/detail";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        questionService.delete(id);
        return "redirect:/QnA/Question/list";
    }
}
