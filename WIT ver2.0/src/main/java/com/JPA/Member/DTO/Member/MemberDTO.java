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
    private int role;

    private MultipartFile boardFile;

    private String memberEmail;
    private String memberName;
    private String originalFileName;
    private String storedFileName;
    private String memberPassword;

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardEntity> boardEntities = new ArrayList<>();

    public String getFileName(String storedFileName) {
        String baseUrl = "https://www.wit.com.s3.ap-northeast-2.amazonaws.com/";
        if (storedFileName.startsWith(baseUrl)) {
            return storedFileName.substring(baseUrl.length());
        }
        return storedFileName; // URL이 아닌 경우 그대로 반환
    }

    private String convertS3Url(String storedFileName) {
        // 변환이 필요한 경우
        String region = "ap-northeast-2";  // 리전
        String bucketName = "www.wit.com";  // 버킷 이름 (www 제외)
        String fileName= getFileName(storedFileName);
        return "https://s3." + region + ".amazonaws.com/" + bucketName + "/" + fileName;
    }

    public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setRole(memberEntity.getRole());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberName(memberEntity.getMemberName());

        if (memberEntity.getFileAttached() == 0) {
            memberDTO.setFileAttached(memberEntity.getFileAttached());
        } else {
            memberDTO.setFileAttached(memberEntity.getFileAttached());
            memberDTO.setOriginalFileName(memberEntity.getMemberProfileEntityList().get(0).getOriginalFileName());

            // 저장된 파일명 가져오기
            String storedFileName = memberEntity.getMemberProfileEntityList().get(0).getStoredFileName();

            storedFileName = memberDTO.convertS3Url(storedFileName);  // S3 URL 변환
            memberDTO.setStoredFileName(storedFileName);  // S3 URL 설정
        }
        return memberDTO;
    }
}