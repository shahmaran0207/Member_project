package com.WayInto.Travel.Service.Guide.Guide;

import com.WayInto.Travel.Repository.Guide.Guide.GuideCommentRepository;
import com.WayInto.Travel.Repository.Guide.Guide.GuideRepository;
import com.WayInto.Travel.Entity.Guide.guide.GuideCommentEntity;
import com.WayInto.Travel.Repository.Member.MemberRepository;
import com.WayInto.Travel.DTO.Guide.guide.GuideCommentDTO;
import com.WayInto.Travel.Entity.Guide.guide.GuideEntity;
import com.WayInto.Travel.Entity.Member.MemberEntity;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GuideCommentService {

    private final GuideRepository guideRepository;
    private final MemberRepository memberRepository;
    private final GuideCommentRepository guideCommentRepository;

    public List<GuideCommentDTO> findAll(Long memberId) {

        GuideEntity guideEntity = guideRepository.findById(memberId).get();

        List<GuideCommentEntity> commentEntityList =
                guideCommentRepository.findAllByGuidecommentTargetOrderByIdDesc(guideEntity);

        List<GuideCommentDTO> commentDTOList = new ArrayList<>();
        for (GuideCommentEntity commentEntity: commentEntityList) {
            GuideCommentDTO commentDTO = GuideCommentDTO.toCommentDTO(commentEntity, memberId);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }

    public Long save(GuideCommentDTO commentDTO) {
        Optional<GuideEntity> optionalCommentTarget =
                guideRepository.findById(commentDTO.getGuidecommentTargetId());

        Optional<MemberEntity> optionalCommenter =
                memberRepository.findById(commentDTO.getMemberId());

        if (optionalCommentTarget.isPresent() && optionalCommenter.isPresent()) {
            GuideEntity targetEntity = optionalCommentTarget.get();
            MemberEntity memberEntity = optionalCommenter.get();

            GuideCommentEntity commentEntity
                    = GuideCommentEntity.toSaveEntity(commentDTO, targetEntity, memberEntity);

            return guideCommentRepository.save(commentEntity).getId();
        } else {
            return null;
        }
    }
}
