package com.WayInto.Travel.Service.Guide;

import com.WayInto.Travel.Repository.Guide.GuideRepository;
import com.WayInto.Travel.Entity.Guide.Guide.GuideEntity;
import com.WayInto.Travel.DTO.Guide.guide.GuideDTO;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GuideService {
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
}
