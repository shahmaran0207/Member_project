package com.WayInto.Travel.DTO.Member;

import org.springframework.web.multipart.MultipartFile;
import com.WayInto.Travel.Entity.Member.MemberEntity;
import com.WayInto.Travel.Entity.Board.BoardEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDTO {
    private Long id;

    private int fileAttached;
    private int role;
    private int likesCount;
    private int hatesCount;
    private int memberMoney;

    private MultipartFile memberProfile;

    private String memberArea;
    private String memberEmail;
    private String memberName;
    private String originalFileName;
    private String storedFileName;
    private String memberPassword;

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardEntity> boardEntities = new ArrayList<>();

    public MemberDTO(Long id, int role, int memberMoney, String memberName, String memberArea, String memberEmail, int likesCount, int hatesCount) {
        this.id = id;
        this.role = role;
        this.memberMoney = memberMoney;
        this.memberName = memberName;
        this.memberArea = memberArea;
        this.memberEmail = memberEmail;
        this.likesCount = likesCount;
        this.hatesCount = hatesCount;
    }

    public String getFileName(String storedFileName) {
        String baseUrl = "https://www.witwit.com.s3.ap-northeast-2.amazonaws.com/";
        if (storedFileName.startsWith(baseUrl)) {
            return storedFileName.substring(baseUrl.length());
        }
        return storedFileName;
    }

    private String convertS3Url(String storedFileName) {
        String region = "ap-northeast-2";
        String bucketName = "www.witwit.com";

        if (storedFileName.startsWith("https://")) {
            storedFileName = storedFileName.substring(storedFileName.lastIndexOf("/") + 1);
        }

        return "https://s3." + region + ".amazonaws.com/" + bucketName + "/" + storedFileName;
    }

    public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setRole(memberEntity.getRole());
        memberDTO.setMemberMoney(memberEntity.getMemberMoney());
        memberDTO.setMemberArea(memberEntity.getMemberArea());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberName(memberEntity.getMemberName());
        memberDTO.setLikesCount(memberEntity.getLikesCount());
        memberDTO.setHatesCount(memberEntity.getHatesCount());

        if (memberEntity.getFileAttached() == 0) {
            memberDTO.setFileAttached(memberEntity.getFileAttached());
        } else {
            memberDTO.setFileAttached(memberEntity.getFileAttached());
            memberDTO.setOriginalFileName(memberEntity.getMemberProfileEntityList().get(0).getOriginalFileName());

            String storedFileName = memberEntity.getMemberProfileEntityList().get(0).getStoredFileName();

            storedFileName = memberDTO.convertS3Url(storedFileName);
            memberDTO.setStoredFileName(storedFileName);
        }
        return memberDTO;
    }
}