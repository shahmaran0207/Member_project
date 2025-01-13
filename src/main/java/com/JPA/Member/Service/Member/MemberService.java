package com.JPA.Member.Service.Member;

import com.JPA.Member.Repository.Member.MemberProfileRepository;
import com.JPA.Member.Repository.Member.MemberRepository;
import com.JPA.Member.Entity.Member.MemberProfileEntity;
import org.springframework.web.multipart.MultipartFile;
import com.google.firebase.auth.FirebaseAuthException;
import com.JPA.Member.Entity.Member.MemberEntity;
import org.springframework.stereotype.Service;
import com.google.firebase.auth.FirebaseAuth;
import com.JPA.Member.DTO.Member.MemberDTO;
import com.google.firebase.auth.UserRecord;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;
import java.io.File;

@Service
public class MemberService {
    private final MemberRepository mr;
    private final S3Client s3Client;
    private final MemberProfileRepository memberProfileRepository;
    private static final String BUCKET_NAME = "www.wit.com"; // S3 버킷 이름
    private static final String S3_BASE_FOLDER = "profile/";

    public MemberService(MemberRepository memberRepository, S3Client s3Client, MemberProfileRepository memberProfileRepository) {
        this.mr = memberRepository;
        this.s3Client = s3Client;
        this.memberProfileRepository = memberProfileRepository;
    }

    public void save(MemberDTO memberDTO) throws IOException, FirebaseAuthException {

        // Firebase Auth 사용자 생성
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(memberDTO.getMemberEmail())
                .setPassword(memberDTO.getMemberPassword());

        UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);

        if (memberDTO.getBoardFile().isEmpty()) {
            // 파일이 없을 경우 처리
            MemberEntity memberEntity = MemberEntity.toSaveEntity(memberDTO);
            mr.save(memberEntity);

        } else {
            // S3에 파일 업로드 처리
            MultipartFile memberProfile = memberDTO.getBoardFile();
            String originalFilename = memberProfile.getOriginalFilename();
            String storedFileName = System.currentTimeMillis() + "_" + originalFilename;

            // 로컬 임시 파일 생성
            Path tempFile = Files.createTempFile("upload-", storedFileName);
            Files.copy(memberProfile.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);

            // S3 업로드
            String s3Key = S3_BASE_FOLDER + storedFileName;
            uploadFileToS3(tempFile, s3Key);

            // Entity 저장
            MemberEntity memberEntity = MemberEntity.toSaveMemberFile(memberDTO);
            Long savedId = mr.save(memberEntity).getId();
            MemberEntity savedBoardEntity = mr.findById(savedId)
                    .orElseThrow(() -> new IllegalStateException("저장된 엔티티를 찾을 수 없습니다."));

            MemberProfileEntity memberProfileEntity = MemberProfileEntity.toMemberProfileEntity(
                    savedBoardEntity, originalFilename, s3Key
            );
            memberProfileRepository.save(memberProfileEntity);

            // 로컬 임시 파일 삭제
            Files.delete(tempFile);
        }
    }

    private void uploadFileToS3(Path filePath, String key) {
        try {
            // 파일 데이터를 포함한 PutObjectRequest 작성
            s3Client.putObject(
                    software.amazon.awssdk.services.s3.model.PutObjectRequest.builder()
                            .bucket(BUCKET_NAME)
                            .key(key)
                            .build(),
                    RequestBody.fromFile(filePath)
            );
            System.out.println("S3 Upload Success: " + key);
        } catch (Exception e) {
            throw new RuntimeException("S3 업로드 중 오류 발생: " + e.getMessage(), e);
        }
    }

    public MemberDTO login(String email) {
        Optional<MemberEntity> bymemberemail = mr.findByMemberEmail(email);

        MemberEntity memberEntity = bymemberemail.get();
        MemberDTO dto= MemberDTO.toMemberDTO(memberEntity);

        return dto;

    }

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = mr.findAll();
        List<MemberDTO> dtoList = new ArrayList<>();

        for(MemberEntity memberEntity : memberEntityList) {
            dtoList.add(MemberDTO.toMemberDTO(memberEntity));
        }
        return dtoList;
    }

    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = mr.findById(id);

        if(optionalMemberEntity.isPresent()) {
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else return null;
    }

    public MemberDTO updateForm(String myEmail) {
        Optional<MemberEntity> optionalMemberEntity = mr.findByMemberEmail(myEmail);

        if(optionalMemberEntity.isPresent()) {
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else return null;
    }

    public void update(MemberDTO memberdto) {
        mr.save(MemberEntity.toUpdateMemberEntity(memberdto));
    }

    public void deleteById(Long id) {
        mr.deleteById(id);
    }

    public String emailCheck(String memberemail) {
        Optional<MemberEntity> optionalMemberEntity = mr.findByMemberEmail(memberemail);
        if(optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            return memberEntity.getMemberEmail();
        } else return null;
    }

    public void guidesave(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        mr.save(memberEntity);
    }
}