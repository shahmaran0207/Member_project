package com.WayInto.Travel.Service.Travel_Review;

import com.WayInto.Travel.Repository.Travel_Review.Travel_ReviewRepository;
import com.WayInto.Travel.Entity.Travel_Review.ReviewEntity;
import com.WayInto.Travel.DTO.Travel_Review.ReviewDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TravelReviewService {
    private final Travel_ReviewRepository travel_ReviewRepository;

    public Page<ReviewDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10;

        Page<ReviewEntity> reviewEntities =
                travel_ReviewRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        Page<ReviewDTO> reviewDTOS = reviewEntities.map(review ->
                new ReviewDTO(review.getId(), review.getHatesCount(),review.getLikesCount(),review.getMemberEntity().getId(),
                        review.getTitle(),review.getContent(),review.getAddress(),review.getCreatedTime(),
                        review.getMemberEntity().getMemberName(), review.getReview_hits(), review.getZipcodeList(), review.getStartDate(), review.getEndDate()
                ));
        return reviewDTOS;
    }

}
