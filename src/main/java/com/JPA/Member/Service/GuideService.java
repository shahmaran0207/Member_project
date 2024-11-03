package com.JPA.Member.Service;

import com.JPA.Member.Repository.MemberRepository;
import com.JPA.Member.Repository.GuideRepository;
import org.springframework.stereotype.Service;
import com.JPA.Member.Entity.MemberEntity;
import com.JPA.Member.Entity.GuideEntity;
import lombok.RequiredArgsConstructor;
import com.JPA.Member.DTO.MemberDTO;
import com.JPA.Member.DTO.GuideDTO;
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
        List<GuideDTO> dtoList = new ArrayList<>();    //DTO 객체를 위한 리스트

        for(GuideEntity guideEntity : guideEntityList) {
            dtoList.add(GuideDTO.toGuideDTO(guideEntity));
        }

        return dtoList;
    }

    public GuideDTO findById(Long id) {
        Optional<GuideEntity> optionalGuideEntity = guideRepository.findById(id);

        if(optionalGuideEntity.isPresent()) {
            return GuideDTO.toGuideDTO(optionalGuideEntity.get());
        } else{
            return null;
        }
    }
}