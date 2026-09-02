package com.WayInto.Travel.Security;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.core.annotation.Order;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import java.io.IOException;

/**
 * 모든 응답에 보안 헤더를 붙인다.
 *
 * 브라우저가 강제해주는 방어라서 비용 대비 효과가 크다.
 * XSS가 하나 뚫려도 CSP가 실행을 막아주는 식으로 층이 겹친다.
 */
@Component
@Order(0)
public class SecurityHeadersFilter extends OncePerRequestFilter {

    private final boolean hstsEnabled;
    private final String contentSecurityPolicy;

    public SecurityHeadersFilter(
            @Value("${app.security.headers.hsts:true}") boolean hstsEnabled,
            @Value("${app.security.headers.csp:}") String contentSecurityPolicy) {
        this.hstsEnabled = hstsEnabled;
        this.contentSecurityPolicy = contentSecurityPolicy;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        // 응답 MIME 타입을 브라우저가 임의로 추측하지 못하게 한다.
        // 업로드된 파일이 스크립트로 해석되는 경로를 막는다.
        response.setHeader("X-Content-Type-Options", "nosniff");

        // 클릭재킹 방지. iframe 삽입 자체를 거부한다.
        response.setHeader("X-Frame-Options", "DENY");

        // 외부로 나갈 때 전체 URL을 넘기지 않는다. 경로에 담긴 식별자 유출을 줄인다.
        response.setHeader("Referrer-Policy", "strict-origin-when-cross-origin");

        // 쓰지 않는 브라우저 기능을 명시적으로 차단한다.
        response.setHeader("Permissions-Policy",
                "camera=(), microphone=(), payment=(), usb=(), geolocation=(self)");

        if (hstsEnabled) {
            // https로만 접속하도록 브라우저에 각인시킨다. http 다운그레이드 공격 방지.
            response.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
        }

        if (!contentSecurityPolicy.isBlank()) {
            response.setHeader("Content-Security-Policy", contentSecurityPolicy);
        }

        chain.doFilter(request, response);
    }
}
