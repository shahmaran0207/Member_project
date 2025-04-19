package com.WayInto.Travel.Service.Guide.TripList;

import com.WayInto.Travel.Repository.Guide.TripList.TripListLikeRepository;
import com.WayInto.Travel.Repository.Guide.TripList.TripListRepository;
import com.WayInto.Travel.Entity.Guide.TripList.TripListLikeEntity;
import org.springframework.transaction.annotation.Transactional;
import com.WayInto.Travel.Entity.Guide.TripList.TripListEntity;
import com.WayInto.Travel.DTO.Guide.TripList.TripListLikeDTO;
import com.WayInto.Travel.Repository.Member.MemberRepository;
import com.WayInto.Travel.Entity.Member.MemberEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TripListLikeService {

    private final TripListRepository tripListRepository;
    private final MemberRepository memberRepository;
    private final TripListLikeRepository tripListLikeRepository;

    @Transactional
    public String toggleLike(TripListLikeDTO tripListLikeDTO) {
        Long tripListId = tripListLikeDTO.getTripListId();
        Long memberId = tripListLikeDTO.getMemberId();

        if (tripListId == null || memberId == null) {
            throw new IllegalArgumentException("TripList ID and Member ID must not be null");
        }

        TripListEntity tripListEntity = tripListRepository.findById(tripListId)
                .orElseThrow(() -> new EntityNotFoundException("TripList not found with id: " + tripListId));
        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberId));

        if (tripListLikeRepository.existsByMemberEntityAndTripListEntity(memberEntity, tripListEntity)) {
            tripListLikeRepository.deleteByMemberEntityAndTripListEntity(memberEntity, tripListEntity);
            tripListEntity.decreaseLikesCount();
            tripListRepository.save(tripListEntity);
            return "Like removed";
        } else {
            TripListLikeEntity tripListLike = TripListLikeEntity.toSaveEntity(memberEntity, tripListEntity);
            tripListLikeRepository.save(tripListLike);
            tripListEntity.increaseLikesCount();
            tripListRepository.save(tripListEntity);
            return "Like added";
        }
    }

    public int getLikeCount(Long TripListId) {
        return tripListLikeRepository.countByTripListEntity(tripListRepository.findById(TripListId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid TripList ID")));
    }

    public boolean isLikedByMember(Long TripListId, Long memberId) {
        TripListEntity tripListEntity = tripListRepository.findById(TripListId)
                .orElseThrow(() -> new EntityNotFoundException("TripList not found with id: " + TripListId));
        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberId));

        return tripListLikeRepository.existsByMemberEntityAndTripListEntity(memberEntity, tripListEntity);
    }
}