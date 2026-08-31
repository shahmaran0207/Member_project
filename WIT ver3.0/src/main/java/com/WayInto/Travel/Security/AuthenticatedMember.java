package com.WayInto.Travel.Security;

/**
 * 서버가 세션 쿠키를 검증해 직접 구성한 신원 정보.
 *
 * 클라이언트가 보낸 값은 절대 여기 들어오지 않는다.
 * Firebase 세션 쿠키 서명 검증을 통과한 뒤 DB에서 조회한 값만 담긴다.
 */
public record AuthenticatedMember(
        Long id,
        String email,
        String name,
        int role,
        int tempGuide,
        Long guideId,
        String firebaseUid
) {
    /** 요청 속성에 실어 나를 때 쓰는 키. */
    public static final String REQUEST_ATTRIBUTE = "WIT_AUTHENTICATED_MEMBER";

    public boolean isGuide() {
        return guideId != null;
    }
}
