package com.JPA.Member.Service.Guide.TripList;

import com.JPA.Member.Repository.Guide.TripList.TripListRepository;
import com.JPA.Member.Entity.Guide.TripList.TripListEntity;
import com.JPA.Member.DTO.Guide.TripList.TripListDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TripListService {
    private final TripListRepository tripListRepository;

    public Page<TripListDTO> paging(Pageable pageable, Long guideId) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10;

        // GuideId와 일치하는 TripListEntity만 가져오기
        Page<TripListEntity> tripListEntities =
                tripListRepository.findByGuideEntityId(guideId, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        // TripListEntity를 TripListDTO로 변환
        Page<TripListDTO> tripListDTOS = tripListEntities.map(triplist ->
                new TripListDTO(triplist.getId(), triplist.getTrip_list(), triplist.getEndDate(), triplist.getStartDate(),
                        triplist.getGuideEntity().getGuideName(), triplist.getZipcodeList(), triplist.getLikesCount(),
                        triplist.getHatesCount(), triplist.getTitle(), triplist.getTrip_list_hits()));
        return tripListDTOS;
    }
}