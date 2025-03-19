package com.JPA.Member.Service.Guide.TripList;

import com.JPA.Member.Repository.Guide.TripList.TripListLikeRepository;
import com.JPA.Member.Repository.Guide.TripList.TripListRepository;
import org.springframework.transaction.annotation.Transactional;
import com.JPA.Member.Entity.Guide.TripList.TripListLikeEntity;
import com.JPA.Member.Repository.Board.BoardLikeRepository;
import com.JPA.Member.Entity.Guide.TripList.TripListEntity;
import com.JPA.Member.DTO.Guide.TripList.TripListLikeDTO;
import com.JPA.Member.Repository.Member.MemberRepository;
import com.JPA.Member.Repository.Board.BoardRepository;
import jakarta.persistence.EntityNotFoundException;
import com.JPA.Member.Entity.Member.MemberEntity;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TripListLikeService {

    private final BoardLikeRepository boardLikeRepository;

    private final BoardRepository boardRepository;

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