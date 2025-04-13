package com.WayInto.Travel.Service.Guide.Guide;

import com.WayInto.Travel.Repository.Guide.Guide.GuideLikeRepository;
import com.WayInto.Travel.Repository.Guide.Guide.GuideRepository;
import org.springframework.transaction.annotation.Transactional;
import com.WayInto.Travel.Repository.Member.MemberRepository;
import com.WayInto.Travel.Entity.Guide.guide.GuideLikeEntity;
import com.WayInto.Travel.Entity.Guide.guide.GuideEntity;
import com.WayInto.Travel.DTO.Guide.guide.GuideLikeDTO;
import com.WayInto.Travel.Entity.Member.MemberEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuideLikeService {

    private final MemberRepository memberRepository;
    private final GuideRepository guideRepository;
    private final GuideLikeRepository guideLikeRepository;

    @Transactional
    public String toggleLike(GuideLikeDTO guideLikeDTO) {
        Long guideId = guideLikeDTO.getGuideId();
        Long memberId = guideLikeDTO.getMemberId();

        if (guideId == null || memberId == null) {
            throw new IllegalArgumentException("Guide ID and Member ID must not be null");
        }

        GuideEntity guideEntity = guideRepository.findById(guideId)
                .orElseThrow(() -> new EntityNotFoundException("Guide not found with id: " + guideId));
        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberId));

        if (guideLikeRepository.existsByMemberEntityAndGuideEntity(memberEntity, guideEntity)) {
            guideLikeRepository.deleteByMemberEntityAndGuideEntity(memberEntity, guideEntity);
            guideEntity.decreaseLikesCount();
            guideRepository.save(guideEntity);
            return "Like removed";
        } else {
            GuideLikeEntity like = GuideLikeEntity.toSaveEntity(memberEntity, guideEntity);
            guideLikeRepository.save(like);
            guideEntity.increaseLikesCount();
            guideRepository.save(guideEntity);
            return "Like added";
        }
    }

    public int getLikeCount(Long guideId) {
        return guideLikeRepository.countByGuideEntity(guideRepository.findById(guideId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Guide ID")));
    }

    public boolean isLikedByMember(Long guideId, Long memberId) {
        GuideEntity guideEntity = guideRepository.findById(guideId)
                .orElseThrow(() -> new EntityNotFoundException("Guide not found with id: " + guideId));
        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberId));

        return guideLikeRepository.existsByMemberEntityAndGuideEntity(memberEntity, guideEntity);
    }
}
