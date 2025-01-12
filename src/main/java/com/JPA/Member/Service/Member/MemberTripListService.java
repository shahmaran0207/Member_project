package com.JPA.Member.Service.Member;

import com.JPA.Member.DTO.Guide.TripList.TripListDTO;
import com.JPA.Member.Repository.Member.MemberTripListRepository;
import com.JPA.Member.Repository.Guide.Guide.GuideRepository;
import com.JPA.Member.Repository.Member.MemberRepository;
import com.JPA.Member.Entity.Member.MemberTripListEntity;
import com.JPA.Member.Entity.Guide.Guide.GuideEntity;
import com.JPA.Member.Entity.Member.MemberEntity;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MemberTripListService {
    private final MemberRepository memberRepository;
    private final MemberTripListRepository memberTripListRepository;
    private final GuideRepository guideRepository;

    public void save(TripListDTO tripListDTO, Long MemberId, Long GuideId) throws IOException {
        MemberEntity memberEntity = memberRepository.findById(MemberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + MemberId));
        GuideEntity guideEntity = guideRepository.findById(GuideId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Guide ID: " + GuideId));

        MemberTripListEntity memberTripListEntity = MemberTripListEntity.toSaveEntity(tripListDTO, guideEntity, memberEntity);
        memberTripListRepository.save(memberTripListEntity);
    }

}