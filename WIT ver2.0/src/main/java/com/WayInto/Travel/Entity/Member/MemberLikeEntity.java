package com.WayInto.Travel.Entity.Member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="Member_Like_Table")
public class MemberLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liker_id", nullable = false)
    private MemberEntity liker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_id", nullable = false)
    private MemberEntity target;

    public static MemberLikeEntity toSaveEntity(MemberEntity liker, MemberEntity target) {
        MemberLikeEntity memberLikeEntity = new MemberLikeEntity();
        memberLikeEntity.setLiker(liker);
        memberLikeEntity.setTarget(target);
        return memberLikeEntity;
    }
}
