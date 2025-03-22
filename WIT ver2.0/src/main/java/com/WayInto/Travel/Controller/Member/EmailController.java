package com.WayInto.Travel.Controller.Member;

import com.WayInto.Travel.Service.Member.EmailService;
import org.springframework.web.bind.annotation.*;
import com.WayInto.Travel.DTO.Member.EmailDTO;
import java.io.UnsupportedEncodingException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/email")
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/send")
    public String mailSend(EmailDTO emailDto) throws MessagingException, UnsupportedEncodingException {
        emailService.sendEmail(emailDto.getMail());
        return "인증코드가 발송되었습니다.";
    }

    @PostMapping("/verify")
    public String verify(EmailDTO emailDto) {
        System.out.println(emailDto.getMail());
        System.out.println(emailDto.getVerifyCode());

        boolean isVerify = emailService.verifyEmailCode(emailDto.getMail(), emailDto.getVerifyCode());
        return isVerify ? "인증이 완료되었습니다." : "인증 실패하셨습니다.";
    }
}