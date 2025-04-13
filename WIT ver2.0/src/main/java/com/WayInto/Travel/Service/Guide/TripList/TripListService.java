package com.WayInto.Travel.Service.Guide.TripList;

import com.WayInto.Travel.Repository.Guide.TripList.TripListRepository;
import com.WayInto.Travel.Repository.Guide.Guide.GuideRepository;
import org.springframework.transaction.annotation.Transactional;
import com.WayInto.Travel.Entity.Guide.TripList.TripListEntity;
import com.WayInto.Travel.Entity.Guide.guide.GuideEntity;
import com.WayInto.Travel.DTO.Guide.TripList.TripListDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import lombok.RequiredArgsConstructor;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TripListService {

    private final GuideRepository guideRepository;
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

    public void save(TripListDTO tripListDTO, Long id) throws IOException {
        GuideEntity guideEntity = guideRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + id));

        TripListEntity tripListEntity = TripListEntity.toSaveEntity(tripListDTO, guideEntity);
        tripListRepository.save(tripListEntity);
    }

    @Transactional
    public void updateHits(Long id) {
        tripListRepository.updateHits(id);
    }

    @Transactional
    public TripListDTO findById(Long id) {
        Optional<TripListEntity> optionalTripListEntity = tripListRepository.findById(id);
        if (optionalTripListEntity.isPresent()) {
            TripListEntity tripListEntity = optionalTripListEntity.get();
            return TripListDTO.toTripListDTO(tripListEntity);
        } else {
            return null;
        }
    }

    public void delete(Long id) {
        tripListRepository.deleteById(id);
    }
}
