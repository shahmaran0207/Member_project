package com.WayInto.Travel.Security;

import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * 클라이언트가 보낸 신원 쿠키를 버리고, 서버가 검증해 만든 값으로 대체한다.
 *
 * ver2의 컨트롤러들은 loginId/memberRole 같은 쿠키를 102곳에서 @CookieValue로 읽는다.
 * 그 호출부를 전부 고치는 대신 요청 자체를 감싸서, 아래 이름의 쿠키에 대해서는
 * 브라우저가 무엇을 보냈든 서버가 확정한 값만 보이게 만든다.
 *
 * 로그인하지 않은 요청에서는 해당 쿠키들이 아예 사라진다.
 * (위조 쿠키를 들고 와도 비로그인으로 취급된다)
 */
public class TrustedIdentityRequestWrapper extends HttpServletRequestWrapper {

    /** 서버가 소유권을 갖는 쿠키 이름. 클라이언트가 보낸 동명 쿠키는 폐기한다. */
    public static final Set<String> MANAGED_COOKIES = Set.of(
            "loginId", "loginEmail", "loginName", "memberRole",
            "GuideID", "tempGuide", "firebaseUid"
    );

    private final Cookie[] cookies;

    public TrustedIdentityRequestWrapper(HttpServletRequest request, AuthenticatedMember member) {
        super(request);
        this.cookies = buildCookies(request.getCookies(), member);
    }

    private static Cookie[] buildCookies(Cookie[] original, AuthenticatedMember member) {
        List<Cookie> result = new ArrayList<>();

        // 서버가 관리하지 않는 쿠키(세션 쿠키 등)는 그대로 통과시킨다.
        if (original != null) {
            Arrays.stream(original)
                    .filter(cookie -> !MANAGED_COOKIES.contains(cookie.getName()))
                    .forEach(result::add);
        }

        if (member != null) {
            result.add(new Cookie("loginId", String.valueOf(member.id())));
            result.add(new Cookie("loginEmail", nullSafe(member.email())));
            result.add(new Cookie("loginName", nullSafe(member.name())));
            result.add(new Cookie("memberRole", String.valueOf(member.role())));
            result.add(new Cookie("tempGuide", String.valueOf(member.tempGuide())));
            result.add(new Cookie("firebaseUid", nullSafe(member.firebaseUid())));
            if (member.guideId() != null) {
                result.add(new Cookie("GuideID", String.valueOf(member.guideId())));
            }
        }

        return result.toArray(new Cookie[0]);
    }

    private static String nullSafe(String value) {
        return value == null ? "" : value;
    }

    @Override
    public Cookie[] getCookies() {
        return cookies.length == 0 ? null : cookies;
    }
}
