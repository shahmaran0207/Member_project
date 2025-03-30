package com.WayInto.Travel.DTO.Guide.guide;

import com.WayInto.Travel.Entity.Guide.Guide.GuideEntity;
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
    private String guideArea;

    private int hateCounts;
    private int likeCounts;

    public static GuideDTO toGuideDTO(GuideEntity guideEntity) {
        GuideDTO guideDTO = new GuideDTO();
        guideDTO.setId(guideEntity.getId());
        guideDTO.setMemberId(guideEntity.getMemberEntity().getId());
        guideDTO.setGuideEmail(guideEntity.getMemberEntity().getMemberEmail());
        guideDTO.setGuideName(guideEntity.getMemberEntity().getMemberName());
        guideDTO.setGuideArea(guideEntity.getMemberEntity().getMemberArea());
        guideDTO.setHateCounts(0);
        guideDTO.setLikeCounts(0);
        return guideDTO;
    }
}