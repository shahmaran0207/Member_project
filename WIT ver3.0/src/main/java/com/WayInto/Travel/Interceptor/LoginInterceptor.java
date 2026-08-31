package com.WayInto.Travel.Interceptor;

import com.WayInto.Travel.Security.AuthenticatedMember;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 보호된 경로에 인증 없이 접근하는 것을 막는다.
 *
 * ver2는 firebaseUid 쿠키가 "존재하는지"만 봤다. 값을 검증하지 않았기 때문에
 * 브라우저에서 아무 값이나 넣으면 그대로 통과했다.
 * 지금은 AuthenticationFilter가 세션 서명을 검증해 심어둔 요청 속성만 신뢰한다.
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object member = request.getAttribute(AuthenticatedMember.REQUEST_ATTRIBUTE);

        if (member == null) {
            if (isAjaxRequest(request)) {
                // 비동기 요청에 로그인 페이지 HTML을 돌려주면 클라이언트가 파싱에 실패한다.
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
            response.sendRedirect("/Member/login");
            return false;
        }
        return true;
    }

    private boolean isAjaxRequest(HttpServletRequest request) {
        String requestedWith = request.getHeader("X-Requested-With");
        String accept = request.getHeader("Accept");
        return "XMLHttpRequest".equals(requestedWith)
                || (accept != null && accept.contains("application/json"));
    }
}
