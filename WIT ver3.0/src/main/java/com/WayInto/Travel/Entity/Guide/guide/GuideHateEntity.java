package com.WayInto.Travel.Entity.Guide.guide;

import com.WayInto.Travel.Entity.Member.MemberEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "guide_hate_table")
public class GuideHateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guide_id", nullable = false)
    private GuideEntity guideEntity;

    public static GuideHateEntity toSaveEntity(MemberEntity member, GuideEntity guide) {
        GuideHateEntity guideHateEntity = new GuideHateEntity();
        guideHateEntity.setMemberEntity(member);
        guideHateEntity.setGuideEntity(guide);
        return guideHateEntity;
    }
}
