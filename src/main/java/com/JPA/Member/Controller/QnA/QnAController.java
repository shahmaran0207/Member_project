package com.JPA.Member.Controller.QnA;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller("/QnA")
public class QnAController {

    @GetMapping("/board")
    @ResponseBody
    public String QnABoard() {
        return "/QnA/board";
    }
}