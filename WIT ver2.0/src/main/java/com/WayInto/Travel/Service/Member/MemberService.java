package com.WayInto.Travel.Service.Member;

import com.WayInto.Travel.Repository.Member.MemberProfileRepository;
import com.WayInto.Travel.Repository.Member.MemberRepository;
import com.WayInto.Travel.Entity.Member.MemberProfileEntity;
import org.springframework.web.multipart.MultipartFile;
import com.google.firebase.auth.FirebaseAuthException;
import com.WayInto.Travel.Entity.Member.MemberEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.WayInto.Travel.Service.ImageService;
import com.WayInto.Travel.DTO.Member.MemberDTO;
import org.springframework.stereotype.Service;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import com.google.firebase.auth.UserRecord;
import lombok.RequiredArgsConstructor;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ImageService imageService;
    private final MemberProfileRepository memberProfileRepository;

    public void save(MemberDTO memberDTO) throws IOException, FirebaseAuthException {

        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(memberDTO.getMemberEmail())
                .setPassword(memberDTO.getMemberPassword());
        UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
        if (memberDTO.getMemberProfile().isEmpty()) {
            MemberEntity memberEntity = MemberEntity.toSaveEntity(memberDTO);
            memberRepository.save(memberEntity);

        } else {
            MultipartFile memberProfile = memberDTO.getMemberProfile();

            String s3Url = imageService.imageUploadFromFile(memberProfile);

            String originalFilename = memberProfile.getOriginalFilename();
            String storedFileName = System.currentTimeMillis() + "_" + originalFilename;

            MemberEntity memberEntity = MemberEntity.toSaveMemberFile(memberDTO);
            Long savedId = memberRepository.save(memberEntity).getId();
            MemberEntity savedBoardEntity = memberRepository.findById(savedId).get();

            MemberProfileEntity memberProfileEntity = MemberProfileEntity.toMemberProfileEntity(savedBoardEntity, storedFileName, s3Url);
            memberProfileRepository.save(memberProfileEntity);
        }
    }

    public String emailCheck(String memberemail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberemail);
        if(optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            return memberEntity.getMemberEmail();
        } else return null;
    }

    public MemberDTO login(String email) {
        Optional<MemberEntity> bymemberemail = memberRepository.findByMemberEmail(email);

        MemberEntity memberEntity = bymemberemail.get();
        MemberDTO dto= MemberDTO.toMemberDTO(memberEntity);

        return dto;
    }

    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);

        if(optionalMemberEntity.isPresent()) {
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else return null;
    }

    public Page<MemberDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10;

        Page<MemberEntity> memberEntities =
                memberRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        Page<MemberDTO> memberDTOS = memberEntities.map(member ->
                new MemberDTO(member.getId(), member.getRole(), member.getMemberMoney(), member.getMemberName(), member.getMemberArea(),
                        member.getMemberEmail(), member.getLikesCount(), member.getHatesCount()));
        return memberDTOS;
    }

}
