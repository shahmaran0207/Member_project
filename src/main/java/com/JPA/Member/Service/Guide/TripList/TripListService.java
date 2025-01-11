package com.JPA.Member.Service.Guide.TripList;

import com.JPA.Member.Repository.Guide.TripList.TripListRepository;
import com.JPA.Member.Repository.Guide.Guide.GuideRepository;
import com.JPA.Member.Entity.Guide.TripList.TripListEntity;
import com.JPA.Member.DTO.Guide.TripList.TripListDTO;
import com.JPA.Member.Entity.Guide.Guide.GuideEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TripListService {
    private final TripListRepository tripListRepository;
    private final GuideRepository guideRepository;

    public Page<TripListDTO> paging(Pageable pageable, Long guideId) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10;

        Page<TripListEntity> tripListEntities =
                tripListRepository.findByGuideEntityId(guideId, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        Page<TripListDTO> tripListDTOS = tripListEntities.map(triplist ->
                new TripListDTO(triplist.getId(), triplist.getTrip_list(), triplist.getGuideEntity().getGuideName(), triplist.getZipcodeList(),
                        triplist.getLikesCount(), triplist.getHatesCount(), triplist.getTitle(), triplist.getTrip_list_hits(), triplist.getSeason(),
                        triplist.getContent(), triplist.getDate()));
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
}