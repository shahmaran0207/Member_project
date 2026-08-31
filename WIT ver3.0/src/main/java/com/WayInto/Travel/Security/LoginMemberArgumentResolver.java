package com.WayInto.Travel.Security;

import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.stereotype.Component;
import org.springframework.core.MethodParameter;
import jakarta.servlet.http.HttpServletRequest;

/**
 * {@link LoginMember}가 붙은 파라미터에 인증 필터가 확정한 신원을 넣어준다.
 */
@Component
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginMember.class)
                && AuthenticatedMember.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        AuthenticatedMember member = request == null ? null
                : (AuthenticatedMember) request.getAttribute(AuthenticatedMember.REQUEST_ATTRIBUTE);

        LoginMember annotation = parameter.getParameterAnnotation(LoginMember.class);
        if (member == null && annotation != null && annotation.required()) {
            throw new UnauthenticatedException("로그인이 필요한 요청입니다.");
        }
        return member;
    }
}
