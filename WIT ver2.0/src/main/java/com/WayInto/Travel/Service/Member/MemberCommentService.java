package com.WayInto.Travel.Service.Member;

import com.WayInto.Travel.Repository.Member.MemberCommentRepository;
import com.WayInto.Travel.Repository.Member.MemberRepository;
import com.WayInto.Travel.Entity.Member.MemberCommentEntity;
import com.WayInto.Travel.DTO.Member.MemberCommentDTO;
import com.WayInto.Travel.Entity.Member.MemberEntity;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberCommentService {

    private final MemberCommentRepository memberCommentRepository;
    private final MemberRepository memberRepository;

    public List<MemberCommentDTO> findAll(Long memberId) {
        MemberEntity memberEntity = memberRepository.findById(memberId).get();
        List<MemberCommentEntity> commentEntityList = memberCommentRepository.findAllByCommentTargetOrderByIdDesc(memberEntity);

        List<MemberCommentDTO> commentDTOList = new ArrayList<>();
        for (MemberCommentEntity commentEntity: commentEntityList) {
            MemberCommentDTO commentDTO = MemberCommentDTO.toCommentDTO(commentEntity, memberId);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }

    public Long save(MemberCommentDTO commentDTO) {
        Optional<MemberEntity> optionalCommentTarget = memberRepository.findById(commentDTO.getCommentTargetId());
        Optional<MemberEntity> optionalCommenter = memberRepository.findById(commentDTO.getMemberId());

        if (optionalCommentTarget.isPresent() && optionalCommenter.isPresent()) {
            MemberEntity targetEntity = optionalCommentTarget.get();
            MemberEntity memberEntity = optionalCommenter.get();

            MemberCommentEntity commentEntity = MemberCommentEntity.toSaveEntity(commentDTO, targetEntity, memberEntity);
            return memberCommentRepository.save(commentEntity).getId();
        } else {
            return null;
        }
    }
}
