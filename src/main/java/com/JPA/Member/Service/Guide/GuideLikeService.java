package com.JPA.Member.Service.Guide;

import org.springframework.transaction.annotation.Transactional;
import com.JPA.Member.Repository.Guide.GuideLikeRepository;
import com.JPA.Member.Repository.Member.MemberRepository;
import com.JPA.Member.Repository.Guide.GuideRepository;
import com.JPA.Member.Entity.Guide.GuideLikeEntity;
import jakarta.persistence.EntityNotFoundException;
import com.JPA.Member.Entity.Member.MemberEntity;
import com.JPA.Member.Entity.Guide.GuideEntity;
import org.springframework.stereotype.Service;
import com.JPA.Member.DTO.Guide.GuideLikeDTO;
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
