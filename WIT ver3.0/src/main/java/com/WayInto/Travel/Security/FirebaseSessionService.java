package com.WayInto.Travel.Security;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.SessionCookieOptions;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Cookie;
import java.time.Duration;

/**
 * Firebase 세션 쿠키의 발급/검증/폐기를 담당한다.
 *
 * ver2는 로그인 시점에만 idToken을 검증하고, 이후에는 평문 쿠키(loginId 등)를 그대로 믿었다.
 * 그래서 브라우저에서 쿠키를 고치면 다른 사람 계정으로 동작할 수 있었다.
 *
 * 세션 쿠키는 Firebase가 서명하므로 클라이언트가 위조할 수 없고,
 * 매 요청마다 서명을 검증해 신원을 서버가 다시 확정한다.
 */
@Service
public class FirebaseSessionService {

    /** 유일하게 브라우저에 저장되는 인증 쿠키. 나머지 신원 정보는 요청마다 서버가 만든다. */
    public static final String SESSION_COOKIE = "WIT_SESSION";

    private final Duration sessionDuration;
    private final boolean cookieSecure;
    private final String cookieSameSite;
    private final boolean checkRevoked;

    public FirebaseSessionService(
            @Value("${app.security.session.duration-minutes:120}") long durationMinutes,
            @Value("${app.security.cookie.secure:true}") boolean cookieSecure,
            @Value("${app.security.cookie.same-site:Lax}") String cookieSameSite,
            @Value("${app.security.session.check-revoked:false}") boolean checkRevoked) {
        this.sessionDuration = Duration.ofMinutes(durationMinutes);
        this.cookieSecure = cookieSecure;
        this.cookieSameSite = cookieSameSite;
        this.checkRevoked = checkRevoked;
    }

    /**
     * 로그인 직후 받은 idToken으로 세션 쿠키를 만든다.
     * Firebase는 발급된 지 5분 이내의 idToken만 받아준다.
     */
    public String createSessionCookie(String idToken) throws FirebaseAuthException {
        SessionCookieOptions options = SessionCookieOptions.builder()
                .setExpiresIn(sessionDuration.toMillis())
                .build();
        return FirebaseAuth.getInstance().createSessionCookie(idToken, options);
    }

    /**
     * 세션 쿠키를 검증한다. 서명/만료가 맞지 않으면 예외가 난다.
     *
     * checkRevoked=true로 두면 로그아웃/탈취 시 즉시 차단되지만
     * 매 요청마다 Firebase 서버로 왕복이 생겨 응답이 느려진다.
     * 기본값은 false(로컬 서명 검증만)이며, 대신 세션 수명을 짧게 가져간다.
     */
    public FirebaseToken verify(String sessionCookie) throws FirebaseAuthException {
        return FirebaseAuth.getInstance().verifySessionCookie(sessionCookie, checkRevoked);
    }

    /**
     * 로그아웃 시 해당 사용자의 리프레시 토큰을 무효화한다.
     * checkRevoked=true인 경우에만 기존 세션 쿠키가 즉시 막힌다.
     */
    public void revoke(String firebaseUid) {
        try {
            FirebaseAuth.getInstance().revokeRefreshTokens(firebaseUid);
        } catch (FirebaseAuthException e) {
            // 폐기 실패가 로그아웃 자체를 막으면 안 된다. 쿠키는 어차피 아래에서 지운다.
        }
    }

    public void writeSessionCookie(HttpServletResponse response, String value) {
        response.addHeader("Set-Cookie", buildCookieHeader(value, sessionDuration.getSeconds()));
    }

    public void clearSessionCookie(HttpServletResponse response) {
        response.addHeader("Set-Cookie", buildCookieHeader("", 0));
    }

    /**
     * SameSite 속성은 jakarta Cookie API로 지정할 수 없어 헤더를 직접 구성한다.
     * SameSite는 CSRF에 대한 1차 방어선이다.
     */
    private String buildCookieHeader(String value, long maxAgeSeconds) {
        StringBuilder header = new StringBuilder()
                .append(SESSION_COOKIE).append('=').append(value)
                .append("; Path=/")
                .append("; Max-Age=").append(maxAgeSeconds)
                .append("; HttpOnly")
                .append("; SameSite=").append(cookieSameSite);
        if (cookieSecure) {
            header.append("; Secure");
        }
        return header.toString();
    }

    public static String readSessionCookie(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return null;
        }
        for (Cookie cookie : request.getCookies()) {
            if (SESSION_COOKIE.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
