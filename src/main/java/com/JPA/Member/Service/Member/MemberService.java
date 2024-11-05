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
import java.io.File;

@Service
public class MemberService {
    private final MemberRepository mr;
    private final MemberProfileRepository memberProfileRepository;

    public MemberService(MemberRepository memberRepository, MemberProfileRepository memberProfileRepository) {
        this.mr = memberRepository;
        this.memberProfileRepository = memberProfileRepository;
    }


    public void save(MemberDTO memberDTO) throws IOException, FirebaseAuthException {

        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(memberDTO.getMemberEmail())
                .setPassword(memberDTO.getMemberPassword());

        UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);


        if (memberDTO.getBoardFile().isEmpty()) {
            MemberEntity memberEntity = MemberEntity.toSaveEntity(memberDTO);
            mr.save(memberEntity);

        } else {
            MultipartFile memberProfile = memberDTO.getBoardFile();
            String originalFilename = memberProfile.getOriginalFilename();
            String storedFileName = System.currentTimeMillis() + "_" + originalFilename;
            String savePath = "C:/Users/wjaud/OneDrive/바탕 화면/MOST IMPORTANT/Member_project/profile/" + storedFileName;
            memberProfile.transferTo(new File(savePath));

            MemberEntity memberEntity = MemberEntity.toSaveMemberFile(memberDTO);
            Long savedId = mr.save(memberEntity).getId();
            MemberEntity savedBoardEntity = mr.findById(savedId).get();

            MemberProfileEntity memberProfileEntity = MemberProfileEntity.toMemberProfileEntity(savedBoardEntity, originalFilename, storedFileName);
            memberProfileRepository.save(memberProfileEntity);
        }
    }

    public MemberDTO login(String email) {
        Optional<MemberEntity> bymemberemail = mr.findByMemberEmail(email);

        MemberEntity memberEntity = bymemberemail.get();
        MemberDTO dto= MemberDTO.toMemberDTO(memberEntity);

        return dto;

    }

    //Repository는 Entity로 주고받음
    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = mr.findAll();
        List<MemberDTO> dtoList = new ArrayList<>();    //DTO 객체를 위한 리스트

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