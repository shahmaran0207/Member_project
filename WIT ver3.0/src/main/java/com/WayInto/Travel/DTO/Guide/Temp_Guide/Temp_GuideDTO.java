package com.WayInto.Travel.DTO.Guide.Temp_Guide;

import com.WayInto.Travel.Entity.Guide.Temp_Guide.Temp_GuideEntity;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Temp_GuideDTO {
    private Long id;
    private Long memberId;

    private String guideEmail;
    private String guideName;
    private String guideArea;

    public Temp_GuideDTO(Long id, Long memberId, String guideEmail, String guideName, String guideArea) {
        this.id = id;
        this.memberId = memberId;
        this.guideEmail = guideEmail;
        this.guideName = guideName;
        this.guideArea = guideArea;
    }

    public static Temp_GuideDTO toTempGuideEntity(Temp_GuideEntity tempGuideEntity) {
        Temp_GuideDTO tempGuideDTO = new Temp_GuideDTO();
        tempGuideDTO.setId(tempGuideEntity.getId());
        tempGuideDTO.setMemberId(tempGuideEntity.getMemberEntity().getId());
        tempGuideDTO.setGuideEmail(tempGuideEntity.getGuideEmail());
        tempGuideDTO.setGuideName(tempGuideEntity.getGuideName());
        tempGuideDTO.setGuideArea(tempGuideEntity.getGuideArea());
        return tempGuideDTO;
    }
}