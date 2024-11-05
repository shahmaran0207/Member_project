package com.JPA.Member.Service.Guide;

import com.JPA.Member.Repository.Member.MemberRepository;
import com.JPA.Member.Repository.Guide.GuideRepository;
import com.JPA.Member.Entity.Member.MemberEntity;
import com.JPA.Member.Entity.Guide.GuideEntity;
import org.springframework.stereotype.Service;
import com.JPA.Member.DTO.Member.MemberDTO;
import com.JPA.Member.DTO.Guide.GuideDTO;
import lombok.RequiredArgsConstructor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GuideService {
    private final GuideRepository guideRepository;
    private final MemberRepository memberRepository;

    public void save(MemberDTO memberDTO, Long id) throws IOException {

        MemberEntity memberEntity = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + id));

            GuideEntity guideEntity = GuideEntity.toSaveEntity(memberDTO, memberEntity);
            guideRepository.save(guideEntity);
    }

    public List<GuideDTO> findAll() {
        List<GuideEntity> guideEntityList = guideRepository.findAll();
        List<GuideDTO> dtoList = new ArrayList<>();

        for(GuideEntity guideEntity : guideEntityList) {
            dtoList.add(GuideDTO.toGuideDTO(guideEntity));
        }

        return dtoList;
    }

    public GuideDTO findById(Long id) {
        Optional<GuideEntity> optionalGuideEntity = guideRepository.findById(id);

        if(optionalGuideEntity.isPresent()) {
            return GuideDTO.toGuideDTO(optionalGuideEntity.get());
        } else return null;
    }
}