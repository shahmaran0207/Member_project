package com.WayInto.Travel.Service.Guide.Guide;

import com.WayInto.Travel.Repository.Guide.Guide.GuideHateRepository;
import com.WayInto.Travel.Repository.Guide.Guide.GuideRepository;
import org.springframework.transaction.annotation.Transactional;
import com.WayInto.Travel.Repository.Member.MemberRepository;
import com.WayInto.Travel.Entity.Guide.guide.GuideHateEntity;
import com.WayInto.Travel.Entity.Guide.guide.GuideEntity;
import com.WayInto.Travel.DTO.Guide.guide.GuideHateDTO;
import com.WayInto.Travel.Entity.Member.MemberEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuideHateService {

    private final GuideHateRepository guideHateRepository;
    private final MemberRepository memberRepository;
    private final GuideRepository guideRepository;

    @Transactional
    public String toggleHate(GuideHateDTO guideHateDTO) {
        Long guideId = guideHateDTO.getGuideId();
        Long memberId = guideHateDTO.getMemberId();

        if (guideId == null || memberId == null) {
            throw new IllegalArgumentException("Guide ID and Member ID must not be null");
        }

        GuideEntity guideEntity = guideRepository.findById(guideId)
                .orElseThrow(() -> new EntityNotFoundException("Guide not found with id: " + guideId));
        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberId));

        if (guideHateRepository.existsByMemberEntityAndGuideEntity(memberEntity, guideEntity)) {
            guideHateRepository.deleteByMemberEntityAndGuideEntity(memberEntity, guideEntity);
            guideEntity.decreaseHatesCount();
            guideRepository.save(guideEntity);
            return "Hate removed";
        } else {
            GuideHateEntity hate = GuideHateEntity.toSaveEntity(memberEntity, guideEntity);
            guideHateRepository.save(hate);
            guideEntity.increaseHatesCount();
            guideRepository.save(guideEntity);
            return "Hate added";
        }
    }

    public int getHateCount(Long guideId) {
        return guideHateRepository.countByGuideEntity(guideRepository.findById(guideId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Guide ID")));
    }

    public boolean isHatedByMember(Long guideId, Long memberId) {
        GuideEntity guideEntity = guideRepository.findById(guideId)
                .orElseThrow(() -> new EntityNotFoundException("Guide not found with id: " + guideId));
        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberId));

        return guideHateRepository.existsByMemberEntityAndGuideEntity(memberEntity, guideEntity);
    }
}
