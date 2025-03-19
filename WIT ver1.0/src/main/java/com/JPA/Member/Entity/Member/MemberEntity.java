package com.JPA.Member.Entity.Member;

//Entity 클래스: 테이블 역할

import com.JPA.Member.DTO.Member.MemberDTO;
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
    private int fileAttached;

    private String memberPassword;

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MemberProfileEntity> memberProfileEntityList = new ArrayList<>();

    public static MemberEntity toMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setRole(memberDTO.getRole());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setFileAttached(memberDTO.getFileAttached());
        return memberEntity;
    }

    public static MemberEntity toUpdateMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDTO.getId());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setRole(memberDTO.getRole());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setFileAttached(memberDTO.getFileAttached());
        return memberEntity;
    }

    public static MemberEntity toSaveEntity(MemberDTO memberDTO) {
        MemberEntity member = new MemberEntity();
        member.setMemberEmail(memberDTO.getMemberEmail());
        member.setMemberName(memberDTO.getMemberName());
        member.setRole(1);
        member.setId(memberDTO.getId());
        member.setFileAttached(0);
        return member;
    }

    public static MemberEntity toSaveMemberFile(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDTO.getId());
        memberEntity.setRole(memberDTO.getRole());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setFileAttached(1);
        return memberEntity;
    }
}