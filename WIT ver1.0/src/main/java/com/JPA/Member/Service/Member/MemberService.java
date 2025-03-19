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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

@Service
public class MemberService {
    private final MemberRepository mr;
    private final ProfileService profileService;
    private final MemberProfileRepository memberProfileRepository;


    public MemberService(MemberRepository memberRepository, ProfileService profileService, MemberProfileRepository memberProfileRepository) {
        this.mr = memberRepository;
        this.profileService = profileService;
        this.memberProfileRepository = memberProfileRepository;
    }

//    public void save(MemberDTO memberDTO) throws IOException, FirebaseAuthException {
//        try {
//            // Firebase 사용자 생성
//            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
//                    .setEmail(memberDTO.getMemberEmail())
//                    .setPassword(memberDTO.getMemberPassword());
//            FirebaseAuth.getInstance().createUser(request);
//
//            // 프로필 파일이 없으면 그냥 저장
//            if (memberDTO.getBoardFile().isEmpty()) {
//                MemberEntity memberEntity = MemberEntity.toSaveEntity(memberDTO);
//                mr.save(memberEntity);
//            } else {
//                // 파일이 있을 경우 S3에 업로드
//                MultipartFile memberProfile = memberDTO.getBoardFile();
//
//                // ImageService를 통해 파일 업로드 및 URL 반환
//                String s3Url = profileService.imageUploadFromFile(memberProfile);
//
//                MemberEntity memberEntity = MemberEntity.toSaveMemberFile(memberDTO);
//                Long savedId = mr.save(memberEntity).getId();
//                MemberEntity savedBoardEntity = mr.findById(savedId)
//                        .orElseThrow(() -> new IllegalStateException("저장된 엔티티를 찾을 수 없습니다."));
//
//                MemberProfileEntity memberProfileEntity = MemberProfileEntity.toMemberProfileEntity(
//                        savedBoardEntity, memberProfile.getOriginalFilename(), s3Url
//                );
//                memberProfileRepository.save(memberProfileEntity);
//            }
//        } catch (FirebaseAuthException e) {
//            throw new RuntimeException("Firebase 사용자 생성 실패: " + e.getMessage(), e);
//        }
//    }

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