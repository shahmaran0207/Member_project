package com.JPA.Member.Service.Guide;

import org.springframework.transaction.annotation.Transactional;
import com.JPA.Member.Repository.Member.MemberRepository;
import com.JPA.Member.Repository.Guide.GuideRepository;
import com.JPA.Member.Repository.Guide.HateRepository;
import jakarta.persistence.EntityNotFoundException;
import com.JPA.Member.Entity.Member.MemberEntity;
import com.JPA.Member.Entity.Guide.GuideEntity;
import com.JPA.Member.Entity.Guide.HateEntity;
import org.springframework.stereotype.Service;
import com.JPA.Member.DTO.Guide.HateDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HateService {

    private final HateRepository hateRepository;
    private final MemberRepository memberRepository;
    private final GuideRepository guideRepository;

    @Transactional
    public String toggleHate(HateDTO hateDTO) {
        Long guideId = hateDTO.getGuideId();
        Long memberId = hateDTO.getMemberId();

        if (guideId == null || memberId == null) {
            throw new IllegalArgumentException("Guide ID and Member ID must not be null");
        }

        GuideEntity guideEntity = guideRepository.findById(guideId)
                .orElseThrow(() -> new EntityNotFoundException("Guide not found with id: " + guideId));
        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberId));

        if (hateRepository.existsByMemberEntityAndGuideEntity(memberEntity, guideEntity)) {
            hateRepository.deleteByMemberEntityAndGuideEntity(memberEntity, guideEntity);
            guideEntity.decreaseHatesCount();
            guideRepository.save(guideEntity);
            return "Hate removed";
        } else {
            HateEntity hate = HateEntity.toSaveEntity(memberEntity, guideEntity);
            hateRepository.save(hate);
            guideEntity.increaseHatesCount();
            guideRepository.save(guideEntity);
            return "Hate added";
        }
    }

    // 특정 게시글에 대한 좋아요 수 반환
    public int getHateCount(Long guideId) {
        return hateRepository.countByGuideEntity(guideRepository.findById(guideId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Guide ID")));
    }

    // 특정 게시글에 대한 좋아요 상태 반환
    public boolean isHatedByMember(Long guideId, Long memberId) {
        GuideEntity guideEntity = guideRepository.findById(guideId)
                .orElseThrow(() -> new EntityNotFoundException("Guide not found with id: " + guideId));
        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberId));

        return hateRepository.existsByMemberEntityAndGuideEntity(memberEntity, guideEntity);
    }
}
