package com.WayInto.Travel.Service.Guide.Guide;

import com.WayInto.Travel.Repository.Guide.Guide.Temp_GuideRepository;
import com.WayInto.Travel.Entity.Guide.Temp_Guide.Temp_GuideEntity;
import org.springframework.transaction.annotation.Transactional;
import com.WayInto.Travel.Repository.Member.MemberRepository;
import com.WayInto.Travel.DTO.Guide.Temp_Guide.Temp_GuideDTO;
import com.WayInto.Travel.Entity.Member.MemberEntity;
import com.WayInto.Travel.DTO.Member.MemberDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Temp_GuideService {

    private final MemberRepository memberRepository;
    private final Temp_GuideRepository tempGuideRepository;

    public void save(MemberDTO memberDTO, Long id) throws IOException {

        MemberEntity memberEntity = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + id));

        Temp_GuideEntity guideEntity = Temp_GuideEntity.toSaveTempGuideEntity(memberDTO, memberEntity);
        tempGuideRepository.save(guideEntity);
    }

    public Page<Temp_GuideDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10;

        Page<Temp_GuideEntity> tempGuideEntities =
                tempGuideRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        Page<Temp_GuideDTO> tempGuideDTOS = tempGuideEntities.map(temp ->
                new Temp_GuideDTO(temp.getId(), temp.getMemberEntity().getId(), temp.getGuideEmail(), temp.getGuideName(), temp.getGuideArea()));
        return tempGuideDTOS;
    }

    @Transactional
    public Temp_GuideDTO findById(Long id) {
        Optional<Temp_GuideEntity> optionalTempGuideEntity = tempGuideRepository.findById(id);

        if (optionalTempGuideEntity.isPresent()) {
            Temp_GuideEntity tempGuideEntity = optionalTempGuideEntity.get();
            return Temp_GuideDTO.toTempGuideEntity(tempGuideEntity);
        } else {
            return null;
        }
    }

    public void deleteTemp(Long memberId) {

        tempGuideRepository.deleteById(memberId);

    }
}
