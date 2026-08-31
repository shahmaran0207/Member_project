package com.WayInto.Travel.Security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 컨트롤러 파라미터에 검증된 로그인 사용자를 주입한다.
 *
 *   public String myPage(@LoginMember AuthenticatedMember member)
 *
 * 쿠키를 직접 읽는 방식(@CookieValue("loginId"))을 대체한다.
 * 쿠키는 클라이언트가 고칠 수 있지만 이 값은 서버가 세션 서명을 검증해 만든 것이다.
 *
 * required=true(기본)인데 비로그인이면 예외를 던진다.
 * 로그인 여부에 따라 화면이 갈리는 곳에서는 required=false로 두고 null을 검사한다.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginMember {
    boolean required() default true;
}
