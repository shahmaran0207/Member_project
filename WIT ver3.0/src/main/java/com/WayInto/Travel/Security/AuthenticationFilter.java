package com.WayInto.Travel.Security;

import com.google.firebase.auth.FirebaseToken;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.stereotype.Component;
import org.springframework.core.annotation.Order;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import java.io.IOException;

/**
 * 모든 요청에서 세션 쿠키를 검증해 신원을 서버가 다시 확정한다.
 *
 * 인터셉터가 아니라 서블릿 필터인 이유는 @CookieValue가 값을 읽기 전에
 * 요청을 감싸야 하기 때문이다.
 */
@Component
@Order(1)
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final FirebaseSessionService sessionService;
    private final AuthenticatedMemberLoader memberLoader;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        AuthenticatedMember member = resolve(request);

        if (member != null) {
            request.setAttribute(AuthenticatedMember.REQUEST_ATTRIBUTE, member);
        }

        // 인증 성공 여부와 무관하게 항상 감싼다.
        // 실패한 경우에도 클라이언트가 보낸 위조 신원 쿠키를 걷어내야 하기 때문이다.
        chain.doFilter(new TrustedIdentityRequestWrapper(request, member), response);
    }

    /**
     * 세션 쿠키 -> Firebase 서명 검증 -> 회원 조회(캐시) 순으로 신원을 복원한다.
     * 어느 단계든 실패하면 비로그인으로 처리한다.
     */
    private AuthenticatedMember resolve(HttpServletRequest request) {
        String sessionCookie = FirebaseSessionService.readSessionCookie(request);
        if (sessionCookie == null || sessionCookie.isBlank()) {
            return null;
        }

        try {
            FirebaseToken token = sessionService.verify(sessionCookie);
            String email = token.getEmail();
            if (email == null || email.isBlank()) {
                return null;
            }
            return memberLoader.load(token.getUid(), email);
        } catch (Exception e) {
            // 서명 불일치, 만료, 폐기된 세션 등 모든 실패를 비로그인으로 수렴시킨다.
            // 실패 사유를 응답으로 흘리지 않는다.
            return null;
        }
    }
}
