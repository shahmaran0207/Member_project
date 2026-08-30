package com.WayInto.Travel.Entity.Guide.Temp_Guide;

import com.WayInto.Travel.Entity.Member.MemberEntity;
import com.WayInto.Travel.DTO.Member.MemberDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "temp_guide_table")
public class Temp_GuideEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String guideEmail;

    @Column
    private String guideName;

    @Column
    private String guideArea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity memberEntity;

    public static Temp_GuideEntity toSaveTempGuideEntity(MemberDTO memberDTO, MemberEntity memberEntity) {
        Temp_GuideEntity guideEntity = new Temp_GuideEntity();
        guideEntity.setGuideName(memberEntity.getMemberName());
        guideEntity.setGuideEmail(memberDTO.getMemberEmail());
        guideEntity.setGuideArea(memberDTO.getMemberArea());
        guideEntity.setMemberEntity(memberEntity);
        return guideEntity;
    }
}