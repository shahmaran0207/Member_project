package com.JPA.Member.DTO.Member;

import org.springframework.web.multipart.MultipartFile;
import com.JPA.Member.Entity.Member.MemberEntity;
import com.JPA.Member.Entity.Board.BoardEntity;
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

    private MultipartFile boardFile;

    private String memberEmail;
    private String memberName;
    private String originalFileName;
    private String storedFileName;
    private String memberPassword;

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardEntity> boardEntities = new ArrayList<>();

    public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberName(memberEntity.getMemberName());

        if (memberEntity.getFileAttached() == 0) {
            memberDTO.setFileAttached(memberEntity.getFileAttached());
        } else {
            memberDTO.setFileAttached(memberEntity.getFileAttached());
            memberDTO.setOriginalFileName(memberEntity.getMemberProfileEntityList().get(0).getOriginalFileName());
            memberDTO.setStoredFileName(memberEntity.getMemberProfileEntityList().get(0).getStoredFileName());
        }
        return memberDTO;
    }
}