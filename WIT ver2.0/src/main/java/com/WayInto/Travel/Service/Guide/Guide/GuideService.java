package com.WayInto.Travel.Service.Guide.Guide;

import com.WayInto.Travel.Repository.Guide.Guide.GuideRepository;
import com.WayInto.Travel.Repository.Member.MemberRepository;
import com.WayInto.Travel.Entity.Guide.guide.GuideEntity;
import com.WayInto.Travel.Entity.Member.MemberEntity;
import com.WayInto.Travel.DTO.Guide.guide.GuideDTO;
import com.WayInto.Travel.DTO.Member.MemberDTO;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Optional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GuideService {

    private final MemberRepository memberRepository;
    private final GuideRepository guideRepository;

    public List<GuideDTO> findAll() {
        List<GuideEntity> guideEntityList = guideRepository.findAll();
        List<GuideDTO> dtoList = new ArrayList<>();

        for(GuideEntity guideEntity : guideEntityList) {
            dtoList.add(GuideDTO.toGuideDTO(guideEntity));
        }

        return dtoList;
    }

    public GuideDTO findByMemberId(Long id) {
        Optional<GuideEntity> optionalGuideEntity = guideRepository.findByMemberEntity_Id(id);

        if(optionalGuideEntity.isPresent()) {
            return GuideDTO.toGuideDTO(optionalGuideEntity.get());
        } else return null;
    }

    public void save(MemberDTO memberDTO, Long id) throws IOException {
        MemberEntity memberEntity = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + id));
        System.out.println("memberDTO"+memberDTO);
        System.out.println("memberEntity"+memberEntity);
        GuideEntity guideEntity = GuideEntity.toSaveEntity(memberDTO, memberEntity);
        guideRepository.save(guideEntity);
    }

    public GuideDTO findById(Long id) {
        Optional<GuideEntity> optionalGuideEntity = guideRepository.findById(id);

        if(optionalGuideEntity.isPresent()) {
            return GuideDTO.toGuideDTO(optionalGuideEntity.get());
        } else return null;
    }
}
