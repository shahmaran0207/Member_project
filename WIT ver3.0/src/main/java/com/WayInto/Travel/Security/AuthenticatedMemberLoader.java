package com.WayInto.Travel.Security;

import com.WayInto.Travel.Service.Guide.Guide.GuideService;
import com.WayInto.Travel.Service.Member.MemberService;
import com.WayInto.Travel.DTO.Guide.guide.GuideDTO;
import com.WayInto.Travel.DTO.Member.MemberDTO;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Duration;
import java.util.HexFormat;

/**
 * 검증된 세션에서 회원 정보를 복원한다.
 *
 * 세션 쿠키 서명 검증 자체는 로컬 연산이라 싸지만, 회원/가이드 조회는 DB를 탄다.
 * 캐시가 없으면 페이지 하나 열 때마다 조회가 2번씩 발생한다.
 * 지도처럼 요청이 잦은 화면에서는 이 비용이 그대로 응답 지연이 된다.
 *
 * TTL을 짧게 잡아, 권한 변경이 반영되는 지연과 DB 부하를 맞바꾼다.
 */
@Component
public class AuthenticatedMemberLoader {

    private final MemberService memberService;
    private final GuideService guideService;
    private final Cache<String, AuthenticatedMember> cache;

    public AuthenticatedMemberLoader(
            MemberService memberService,
            GuideService guideService,
            @Value("${app.security.principal-cache.ttl-seconds:60}") long ttlSeconds,
            @Value("${app.security.principal-cache.max-size:10000}") long maxSize) {
        this.memberService = memberService;
        this.guideService = guideService;
        this.cache = Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofSeconds(ttlSeconds))
                .maximumSize(maxSize)
                .build();
    }

    /**
     * @param firebaseUid 서명 검증을 통과한 세션에서 꺼낸 uid
     * @param email       같은 세션에서 꺼낸 이메일
     * @return 조회 실패 시 null (비로그인 처리)
     */
    public AuthenticatedMember load(String firebaseUid, String email) {
        // uid를 그대로 키로 쓰면 캐시 덤프에 식별자가 남는다. 해시로 바꿔 저장한다.
        String key = hash(firebaseUid);
        return cache.get(key, ignored -> loadFromDatabase(firebaseUid, email));
    }

    /** 로그아웃/권한 변경 직후 캐시를 즉시 비운다. */
    public void evict(String firebaseUid) {
        cache.invalidate(hash(firebaseUid));
    }

    private AuthenticatedMember loadFromDatabase(String firebaseUid, String email) {
        try {
            MemberDTO memberDTO = memberService.login(email);
            if (memberDTO == null || memberDTO.getId() == null) {
                return null;
            }

            GuideDTO guideDTO = guideService.findByMemberId(memberDTO.getId());

            return new AuthenticatedMember(
                    memberDTO.getId(),
                    memberDTO.getMemberEmail(),
                    memberDTO.getMemberName(),
                    memberDTO.getRole(),
                    memberDTO.getTempGuide(),
                    guideDTO != null ? guideDTO.getId() : null,
                    firebaseUid
            );
        } catch (Exception e) {
            // 탈퇴 등으로 회원이 사라진 경우. 세션은 유효해도 비로그인으로 떨어뜨린다.
            return null;
        }
    }

    private String hash(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(digest.digest(value.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new IllegalStateException("SHA-256 unavailable", e);
        }
    }
}
