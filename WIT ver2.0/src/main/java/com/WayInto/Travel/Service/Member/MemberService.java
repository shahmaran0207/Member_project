package com.WayInto.Travel.Service.Member;

import com.WayInto.Travel.Repository.Member.MemberProfileRepository;
import com.WayInto.Travel.Repository.Guide.Guide.GuideRepository;
import com.WayInto.Travel.Repository.Member.MemberRepository;
import com.WayInto.Travel.Entity.Member.MemberProfileEntity;
import com.WayInto.Travel.Entity.Guide.guide.GuideEntity;
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
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final GuideRepository guideRepository;
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

    public Page<MemberDTO> paging(Pageable pageable, String area) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10;

        Page<MemberEntity> memberEntities;

        PageRequest pageRequest = PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id"));

        if (area == null || area.isEmpty()) {
            memberEntities = memberRepository.findAll(pageRequest);
        } else {
            memberEntities = memberRepository.findByMemberArea(area, pageRequest);
        }

        return memberEntities.map(member ->
                new MemberDTO(member.getId(), member.getRole(), member.getMemberMoney(), member.getMemberName(), member.getMemberArea(),
                        member.getMemberEmail(), member.getLikesCount(), member.getHatesCount(), member.getTempGuide(), member.getTotalAttendance()));
    }

    public void saveTempGuide(Long memberId) {
        MemberEntity existingMemberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid board ID: " + memberId));

        existingMemberEntity.setTempGuide(1);
        memberRepository.save(existingMemberEntity);
    }

    public MemberDTO deleteTemp(Long memberId) {
        MemberEntity existingMemberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid board ID: " + memberId));

        existingMemberEntity.setTempGuide(0);
        memberRepository.save(existingMemberEntity);

        MemberDTO memberDTO = MemberDTO.toMemberDTO(existingMemberEntity);

        return  memberDTO;
    }

    public void buyPoint(Long memberId, MemberDTO memberDTO) {

        MemberEntity exisitingMemberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + memberDTO.getId()));

        exisitingMemberEntity.setMemberMoney(exisitingMemberEntity.getMemberMoney()+ memberDTO.getMemberMoney());
        memberRepository.save(exisitingMemberEntity);
    }

    public MemberDTO update(MemberDTO memberDTO) throws IOException, FirebaseAuthException {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(memberDTO.getMemberEmail())
                .setPassword(memberDTO.getMemberPassword());
        UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);

        MemberEntity existingMemberEntity = memberRepository.findById(memberDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + memberDTO.getId()));

        if (!memberDTO.getMemberProfile().isEmpty()) {
            List<MemberProfileEntity> existingProfiles = memberProfileRepository.findByMemberEntity(existingMemberEntity);

            for (MemberProfileEntity profile : existingProfiles) {
                imageService.deleteImage(profile.getStoredFileName());
                memberProfileRepository.delete(profile);
            }

            MultipartFile memberProfile = memberDTO.getMemberProfile();
            String s3Url = imageService.imageUploadFromFile(memberProfile);

            String originalFilename = memberProfile.getOriginalFilename();
            String storedFileName = System.currentTimeMillis() + "_" + originalFilename;

            MemberProfileEntity memberProfileEntity = MemberProfileEntity.toMemberProfileEntity(existingMemberEntity, storedFileName, s3Url);
            memberProfileRepository.save(memberProfileEntity);
        }

        if (!memberDTO.getMemberName().isEmpty()) {
            existingMemberEntity.setMemberName(memberDTO.getMemberName());
        }

        existingMemberEntity.setHatesCount(existingMemberEntity.getHatesCount());
        existingMemberEntity.setLikesCount(existingMemberEntity.getLikesCount());
        existingMemberEntity.setMemberArea(existingMemberEntity.getMemberArea());
        existingMemberEntity.setMemberMoney(existingMemberEntity.getMemberMoney());
        existingMemberEntity.setMemberName(existingMemberEntity.getMemberName());
        existingMemberEntity.setRole(existingMemberEntity.getRole());
        existingMemberEntity.setTempGuide(existingMemberEntity.getTempGuide());
        existingMemberEntity.setTotalAttendance(existingMemberEntity.getTotalAttendance());

        memberRepository.save(existingMemberEntity);
        return findById(memberDTO.getId());
    }

    public void sellTripList(Long guideId, int price) {
        GuideEntity guideEntity = guideRepository.findById(guideId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid guide ID: " + guideId));

        MemberEntity existingMemberEntity = guideEntity.getMemberEntity();

        existingMemberEntity.setMemberMoney(existingMemberEntity.getMemberMoney() + price);

        memberRepository.save(existingMemberEntity);
    }
}
