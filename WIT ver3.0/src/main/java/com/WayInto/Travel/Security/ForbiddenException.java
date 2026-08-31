package com.WayInto.Travel.Security;

/** 로그인은 했지만 해당 리소스에 대한 권한이 없을 때. */
public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}
