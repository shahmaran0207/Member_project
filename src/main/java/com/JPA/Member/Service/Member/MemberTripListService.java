package com.JPA.Member.Service.Member;

import com.JPA.Member.Repository.Member.MemberTripListRepository;
import com.JPA.Member.Repository.Member.MemberRepository;
import com.JPA.Member.Entity.Member.MemberTripListEntity;
import com.JPA.Member.DTO.Guide.TripList.TripListDTO;
import com.JPA.Member.DTO.Member.MemberTripListDTO;
import org.springframework.data.domain.PageRequest;
import com.JPA.Member.Entity.Member.MemberEntity;
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

    public void save(TripListDTO tripListDTO, Long MemberId) throws IOException {
        MemberEntity memberEntity = memberRepository.findById(MemberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + MemberId));

        MemberTripListEntity memberTripListEntity = MemberTripListEntity.toSaveEntity(tripListDTO, memberEntity);
        memberTripListRepository.save(memberTripListEntity);
    }

    public Page<MemberTripListDTO> paging(Pageable pageable, Long MemberId) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10;

        Page<MemberTripListEntity> memberTripListEntities =
                memberTripListRepository.findByMemberEntityId(MemberId, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        Page<MemberTripListDTO> tripListDTOS = memberTripListEntities.map(triplist ->
                new MemberTripListDTO(triplist.getId(), triplist.getTrip_list(), triplist.getZipcodeList(),
                        triplist.getTitle(), triplist.getSeason(),triplist.getContent(), triplist.getDate(),triplist.getPrice(),triplist.getMemberEntity().getId()));

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
}