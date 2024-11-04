package com.JPA.Member.DTO.Guide;

import com.JPA.Member.Entity.Guide.GuideEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GuideDTO {
    private Long id;
    private Long memberId;

    private String guideEmail;
    private String guideName;

    private int hateCounts;

    public static GuideDTO toGuideDTO(GuideEntity guideEntity) {
        GuideDTO guideDTO = new GuideDTO();
        guideDTO.setId(guideEntity.getId());
        guideDTO.setMemberId(guideEntity.getMemberEntity().getId());
        guideDTO.setGuideEmail(guideEntity.getMemberEntity().getMemberEmail());
        guideDTO.setGuideName(guideEntity.getMemberEntity().getMemberName());
        guideDTO.setHateCounts(0);
        return guideDTO;
    }

}