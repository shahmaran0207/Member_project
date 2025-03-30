package com.WayInto.Travel.Entity.Guide.Guide;

import com.WayInto.Travel.Entity.Member.MemberEntity;
import com.WayInto.Travel.DTO.Member.MemberDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "guide_table")
public class GuideEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String guideEmail;

    @Column
    private String guideName;

    @Column
    private String guideArea;

    @Column
    private int hateCounts;

    @Column
    private int likeCounts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity memberEntity;

    public void increaseHatesCount() {
        this.hateCounts++;
    }

    public void decreaseHatesCount() {
        if (this.hateCounts > 0) {
            this.hateCounts--;
        }
    }

    public void increaseLikesCount() {
        this.likeCounts++;
    }

    public void decreaseLikesCount() {
        if (this.likeCounts > 0) {
            this.likeCounts--;
        }
    }

    public static GuideEntity toSaveEntity(MemberDTO memberDTO, MemberEntity memberEntity) {
        GuideEntity guideEntity = new GuideEntity();
        guideEntity.setGuideName(memberEntity.getMemberName());
        guideEntity.setGuideEmail(memberDTO.getMemberEmail());
        guideEntity.setGuideArea(memberDTO.getMemberArea());
        guideEntity.setMemberEntity(memberEntity);
        return guideEntity;
    }
}