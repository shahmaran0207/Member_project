package com.WayInto.Travel.Service.Guide.TripList;

import com.WayInto.Travel.Repository.Guide.TripList.TripListRepository;
import com.WayInto.Travel.Entity.Guide.TripList.TripListEntity;
import com.WayInto.Travel.DTO.Guide.TripList.TripListDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TripListService {

    private final TripListRepository tripListRepository;

    public Page<TripListDTO> paging(Pageable pageable, Long guideId) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10;

        Page<TripListEntity> tripListEntities =
                tripListRepository.findByGuideEntityId(guideId, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        Page<TripListDTO> tripListDTOS = tripListEntities.map(triplist ->
                new TripListDTO(triplist.getId(), triplist.getTrip_list(), triplist.getGuideEntity().getGuideName(), triplist.getZipcodeList(),
                        triplist.getLikesCount(), triplist.getHatesCount(), triplist.getTitle(), triplist.getTrip_list_hits(), triplist.getSeason(),
                        triplist.getContent(), triplist.getDate(), triplist.getPrice()));
        return tripListDTOS;
    }

}
