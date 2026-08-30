package com.WayInto.Travel.Controller.ControllerAdvice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Cookie;

@Component
@ControllerAdvice
public class GlobalControllerAdvice {

    /**
     * 카카오맵 JS SDK 키. 브라우저에 노출되는 것이 정상인 키라 템플릿으로 내려보낸다.
     * 다만 카카오 콘솔에서 도메인 화이트리스트를 걸어야 타 사이트의 쿼터 소모를 막을 수 있다.
     * 서버 전용인 REST 키(app.map.kakao.rest-key)는 여기서 절대 노출하지 않는다.
     */
    @Value("${app.map.kakao.js-key:}")
    private String kakaoJsKey;

    @ModelAttribute("kakaoJsKey")
    public String addKakaoJsKeyToModel() {
        return kakaoJsKey;
    }

    @ModelAttribute("isLoggedIn")
    public boolean addLoginStatusToModel(HttpServletRequest request) {
        String loginId = getCookieValue(request, "loginId");
        return loginId != null;
    }

    public String getCookieValue(HttpServletRequest request, String cookieName) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
