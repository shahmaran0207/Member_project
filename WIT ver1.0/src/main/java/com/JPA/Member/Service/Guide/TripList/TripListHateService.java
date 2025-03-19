package com.JPA.Member.Service.Guide.TripList;

import com.JPA.Member.Repository.Guide.TripList.TripListHateRepository;
import com.JPA.Member.Repository.Guide.TripList.TripListRepository;
import org.springframework.transaction.annotation.Transactional;
import com.JPA.Member.Entity.Guide.TripList.TripListHateEntity;
import com.JPA.Member.Entity.Guide.TripList.TripListEntity;
import com.JPA.Member.Repository.Member.MemberRepository;
import com.JPA.Member.DTO.Guide.TripList.TripListHateDTO;
import jakarta.persistence.EntityNotFoundException;
import com.JPA.Member.Entity.Member.MemberEntity;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TripListHateService {

    private final MemberRepository memberRepository;
    private final TripListRepository tripListRepository;
    private final TripListHateRepository tripListHateRepository;

    @Transactional
    public String toggleHate(TripListHateDTO tripListHateDTO) {
        Long TripListId = tripListHateDTO.getTripListId();
        Long memberId = tripListHateDTO.getMemberId();

        if (TripListId == null || memberId == null) {
            throw new IllegalArgumentException("TripList ID and Member ID must not be null");
        }

        TripListEntity tripListEntity = tripListRepository.findById(TripListId)
                .orElseThrow(() -> new EntityNotFoundException("TripList not found with id: " + TripListId));

        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberId));

        if (tripListHateRepository.existsByMemberEntityAndTripListEntity(memberEntity, tripListEntity)) {
            tripListHateRepository.deleteByMemberEntityAndTripListEntity(memberEntity, tripListEntity);
            tripListEntity.decreaseHatesCount();
            tripListRepository.save(tripListEntity);
            return "Like removed";
        } else {
            TripListHateEntity tripListHateEntity = TripListHateEntity.toSaveEntity(memberEntity, tripListEntity);
            tripListHateRepository.save(tripListHateEntity);
            tripListEntity.increaseHatesCount();
            tripListRepository.save(tripListEntity);
            return "Like added";
        }
    }

    public int getHateCount(Long TripListId) {
        return tripListHateRepository.countByTripListEntity(tripListRepository.findById(TripListId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid TripList ID")));
    }

    public boolean isHatedByMember(Long TripListId, Long memberId) {
        TripListEntity tripListEntity = tripListRepository.findById(TripListId)
                .orElseThrow(() -> new EntityNotFoundException("TripList not found with id: " + TripListId));
        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberId));

        return tripListHateRepository.existsByMemberEntityAndTripListEntity(memberEntity, tripListEntity);
    }
}
