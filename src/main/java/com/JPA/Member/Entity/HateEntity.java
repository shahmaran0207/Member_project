package com.JPA.Member.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "hate_table")
public class HateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guide_id", nullable = false)
    private GuideEntity guideEntity;

    public static HateEntity toSaveEntity(MemberEntity member, GuideEntity guide) {
        HateEntity hateEntity = new HateEntity();
        hateEntity.setMemberEntity(member);
        hateEntity.setGuideEntity(guide);
        return hateEntity;
    }
}
