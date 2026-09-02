package com.WayInto.Travel.Security;

import org.springframework.stereotype.Component;

/**
 * 리소스 소유권을 서버에서 강제한다.
 *
 * ver2는 삭제/수정 버튼을 템플릿에서 숨기는 것으로 권한을 대신했다.
 * (예: Board/detail.html의 th:if="board.memberId == loginId or memberRole == 2")
 * 화면에서 감추는 것은 보안이 아니다. URL을 직접 호출하면 그대로 통과했다.
 *
 * 여기서 던지는 예외는 SecurityExceptionHandler가 401/403으로 변환한다.
 */
@Component
public class ResourceGuard {

    /** 관리자 role 값. 템플릿의 memberRole == 2 분기와 같은 의미다. */
    public static final int ROLE_ADMIN = 2;

    /**
     * 소유자 본인이거나 관리자일 때만 통과시킨다.
     *
     * @param member  인증 필터가 확정한 신원 (비로그인이면 null)
     * @param ownerId 대상 리소스를 만든 회원의 id
     */
    public void requireOwnerOrAdmin(AuthenticatedMember member, Long ownerId) {
        if (member == null) {
            throw new UnauthenticatedException("로그인이 필요한 요청입니다.");
        }
        if (isAdmin(member)) {
            return;
        }
        if (ownerId != null && ownerId.equals(member.id())) {
            return;
        }
        // 리소스의 존재 여부나 소유자를 알려주지 않는다.
        throw new ForbiddenException("이 리소스에 대한 권한이 없습니다.");
    }

    public void requireAdmin(AuthenticatedMember member) {
        if (member == null) {
            throw new UnauthenticatedException("로그인이 필요한 요청입니다.");
        }
        if (!isAdmin(member)) {
            throw new ForbiddenException("관리자 권한이 필요합니다.");
        }
    }

    /** 대상 리소스 자체가 없을 때. 권한 판단 이전에 걸러낸다. */
    public void requireFound(Object resource) {
        if (resource == null) {
            throw new ForbiddenException("존재하지 않는 리소스입니다.");
        }
    }

    public boolean isAdmin(AuthenticatedMember member) {
        return member != null && member.role() == ROLE_ADMIN;
    }
}
