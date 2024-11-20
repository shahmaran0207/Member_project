package com.JPA.Member.Entity.Guide;

import com.JPA.Member.Entity.Member.MemberEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "like_table")
public class GuideLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guide_id", nullable = false)
    private GuideEntity guideEntity;

    public static GuideLikeEntity toSaveEntity(MemberEntity member, GuideEntity guide) {
        GuideLikeEntity guideLikeEntity = new GuideLikeEntity();
        guideLikeEntity.setMemberEntity(member);
        guideLikeEntity.setGuideEntity(guide);
        return guideLikeEntity;
    }
}
