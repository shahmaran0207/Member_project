package com.WayInto.Travel.Security;

/** 로그인이 필요한 요청에 인증된 신원이 없을 때. */
public class UnauthenticatedException extends RuntimeException {
    public UnauthenticatedException(String message) {
        super(message);
    }
}
