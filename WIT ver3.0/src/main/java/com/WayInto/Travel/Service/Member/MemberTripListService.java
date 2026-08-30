package com.WayInto.Travel.Service.Member;

import com.WayInto.Travel.Repository.Guide.TripList.TripListRepository;
import com.WayInto.Travel.Repository.Member.MemberTripListRepository;
import com.WayInto.Travel.Entity.Guide.TripList.TripListEntity;
import com.WayInto.Travel.Entity.Member.MemberTripListEntity;
import com.WayInto.Travel.Repository.Member.MemberRepository;
import com.WayInto.Travel.DTO.Guide.TripList.TripListDTO;
import com.WayInto.Travel.DTO.Member.MemberTripListDTO;
import com.WayInto.Travel.Entity.Member.MemberEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberTripListService {

    private final MemberRepository memberRepository;
    private final MemberTripListRepository memberTripListRepository;
    private final TripListRepository tripListRepository;

    public void save(TripListDTO tripListDTO, Long MemberId, Long TripListId) throws IOException {
        MemberEntity memberEntity = memberRepository.findById(MemberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + MemberId));

        TripListEntity tripListEntity = tripListRepository.findById(TripListId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid TripList ID: " + TripListId));

        MemberTripListEntity memberTripListEntity = MemberTripListEntity.toSaveEntity(tripListDTO, memberEntity, tripListEntity);
        memberTripListRepository.save(memberTripListEntity);
    }

    public Page<MemberTripListDTO> paging(Pageable pageable, Long MemberId) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10;

        Page<MemberTripListEntity> memberTripListEntities =
                memberTripListRepository.findByMemberEntity_Id(MemberId, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        Page<MemberTripListDTO> tripListDTOS = memberTripListEntities.map(triplist ->
                new MemberTripListDTO(triplist.getId(), triplist.getTrip_list(), triplist.getZipcodeList(),
                        triplist.getTitle(), triplist.getSeason(),triplist.getContent(), triplist.getDate(),triplist.getPrice(),triplist.getMemberEntity().getId(),
                        triplist.getTripListEntity().getId()));

        return tripListDTOS;
    }

    @Transactional
    public MemberTripListDTO findById(Long id) {
        Optional<MemberTripListEntity> optionalMemberTripListEntity = memberTripListRepository.findById(id);
        if (optionalMemberTripListEntity.isPresent()) {
            MemberTripListEntity memberTripListEntity = optionalMemberTripListEntity.get();
            return MemberTripListDTO.toTripListDTO(memberTripListEntity);
        } else {
            return null;
        }
    }

    @Transactional
    public MemberTripListDTO findByMemberId(Long id) {
        Optional<MemberTripListEntity> optionalMemberTripListEntity = memberTripListRepository.findFirstByMemberTripListEntityId(id);
        if (optionalMemberTripListEntity.isPresent()) {
            MemberTripListEntity memberTripListEntity = optionalMemberTripListEntity.get();
            return MemberTripListDTO.toTripListDTO(memberTripListEntity);
        } else {
            return null;
        }
    }
}
