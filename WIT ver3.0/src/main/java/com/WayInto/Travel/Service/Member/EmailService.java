package com.WayInto.Travel.Service.Member;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.io.UnsupportedEncodingException;
import com.WayInto.Travel.Config.RedisUtil;
import jakarta.mail.internet.MimeUtility;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.thymeleaf.context.Context;
import org.thymeleaf.TemplateEngine;
import lombok.extern.slf4j.Slf4j;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final RedisUtil redisUtil;
    private final TemplateEngine templateEngine;

    private static final String SENDER_EMAIL = "shahmaran0207@gmail.com";
    private static final String EMAIL_SUBJECT = "[WIT - Way Into Travel] 회원가입 인증 코드 안내";

    private static final int CODE_LENGTH = 6;

    private static final long CODE_EXPIRE_TIME = 60 * 30L;

    private String createCode() {
        int leftLimit = 48;
        int rightLimit = 122;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(CODE_LENGTH)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private String setContext(String code) {
        Context context = new Context();
        context.setVariable("code", code);
        log.info("Generated code: {}", code);

        String content = templateEngine.process("mail", context);

        return content;
    }

    private MimeMessage createEmailForm(String email) throws MessagingException, UnsupportedEncodingException {
        String authCode = createCode();

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(email);
        helper.setSubject(MimeUtility.encodeText(EMAIL_SUBJECT, "UTF-8", "B"));
        helper.setFrom(SENDER_EMAIL);

        String content = setContext(authCode);
        helper.setText(content, true);

        redisUtil.setDataExpire(email, authCode, CODE_EXPIRE_TIME);

        return message;
    }

    public void sendEmail(String toEmail) throws MessagingException, UnsupportedEncodingException {
        if (redisUtil.existData(toEmail)) {
            redisUtil.deleteData(toEmail);
        }

        MimeMessage emailForm = createEmailForm(toEmail);
        javaMailSender.send(emailForm);
    }

    public Boolean verifyEmailCode(String email, String code) {
        String codeFoundByEmail = redisUtil.getData(email);
        return code.equals(codeFoundByEmail);
    }
}
