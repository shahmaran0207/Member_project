package com.WayInto.Travel.Controller.ControllerAdvice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import com.WayInto.Travel.Security.UnauthenticatedException;
import com.WayInto.Travel.Security.ForbiddenException;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

/**
 * 인증/인가 실패를 사용자에게 보여줄 형태로 변환한다.
 *
 * 실패 사유를 자세히 알려주지 않는다.
 * "글이 없음"과 "권한이 없음"을 구분해 응답하면 리소스 존재 여부를 캐낼 수 있게 된다.
 *
 * 반환 타입이 Object인 것은 화면 요청과 비동기 요청의 응답 형태가 다르기 때문이다.
 * 비동기 요청에 로그인 페이지 HTML을 돌려주면 클라이언트가 파싱에 실패한다.
 */
@ControllerAdvice
public class SecurityExceptionHandler {

    @ExceptionHandler(UnauthenticatedException.class)
    public Object handleUnauthenticated(HttpServletRequest request) {
        if (isAsyncRequest(request)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return new ModelAndView("redirect:/Member/login");
    }

    @ExceptionHandler(ForbiddenException.class)
    public Object handleForbidden(HttpServletRequest request) {
        if (isAsyncRequest(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        ModelAndView mav = new ModelAndView("alert");
        mav.setStatus(HttpStatus.FORBIDDEN);
        mav.addObject("msg", "권한이 없습니다.");
        mav.addObject("redirectUrl", "/");
        return mav;
    }

    private boolean isAsyncRequest(HttpServletRequest request) {
        String requestedWith = request.getHeader("X-Requested-With");
        String accept = request.getHeader("Accept");
        return "XMLHttpRequest".equals(requestedWith)
                || (accept != null && accept.contains("application/json"));
    }
}
