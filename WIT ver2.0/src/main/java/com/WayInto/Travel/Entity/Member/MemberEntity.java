package com.WayInto.Travel.Entity.Member;

import com.WayInto.Travel.DTO.Member.MemberDTO;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "member_table")
public class MemberEntity {
    @Id // pk 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String memberEmail;

    @Column
    private String memberName;

    @Column
    private int role;

    @Column
    private int likesCount;

    @Column
    private int hatesCount;

    @Column
    private int memberMoney;

    @Column
    private String memberArea;

    @Column
    private int fileAttached;

    private String memberPassword;

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MemberProfileEntity> memberProfileEntityList = new ArrayList<>();

    public static MemberEntity toMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setRole(memberDTO.getRole());
        memberEntity.setMemberMoney(memberDTO.getMemberMoney());
        memberEntity.setMemberArea(memberDTO.getMemberArea());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setLikesCount(memberDTO.getLikesCount());
        memberEntity.setHatesCount(memberDTO.getHatesCount());
        memberEntity.setFileAttached(memberDTO.getFileAttached());
        return memberEntity;
    }

    public static MemberEntity toUpdateMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDTO.getId());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setRole(memberDTO.getRole());
        memberEntity.setMemberMoney(memberDTO.getMemberMoney());
        memberEntity.setMemberArea(memberDTO.getMemberArea());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setLikesCount(memberDTO.getLikesCount());
        memberEntity.setHatesCount(memberDTO.getHatesCount());
        memberEntity.setFileAttached(memberDTO.getFileAttached());
        return memberEntity;
    }

    public static MemberEntity toSaveEntity(MemberDTO memberDTO) {
        MemberEntity member = new MemberEntity();
        member.setMemberEmail(memberDTO.getMemberEmail());
        member.setMemberName(memberDTO.getMemberName());
        member.setMemberArea(memberDTO.getMemberArea());
        member.setLikesCount(0);
        member.setHatesCount(0);
        member.setMemberMoney(0);
        member.setRole(1);
        member.setId(memberDTO.getId());
        member.setFileAttached(0);
        return member;
    }

    public static MemberEntity toSaveMemberFile(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDTO.getId());
        memberEntity.setMemberArea(memberDTO.getMemberArea());
        memberEntity.setRole(memberDTO.getRole());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setFileAttached(1);
        memberEntity.setLikesCount(0);
        memberEntity.setHatesCount(0);
        memberEntity.setMemberMoney(0);
        return memberEntity;
    }
}