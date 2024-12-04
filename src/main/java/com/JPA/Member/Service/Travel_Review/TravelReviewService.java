package com.JPA.Member.Service.Travel_Review;

import com.JPA.Member.Repository.Travel_Review.TravelReviewRepository;
import com.JPA.Member.Entity.Travel_Review.ReviewEntity;
import org.springframework.data.domain.PageRequest;
import com.JPA.Member.DTO.Travel_Review.ReviewDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TravelReviewService {
    private final TravelReviewRepository travelReviewRepository;

    @Transactional
    public List<ReviewDTO> findAll() {
        List<ReviewEntity> reviewEntityList = travelReviewRepository.findAll();
        List<ReviewDTO> reviewDTOList = new ArrayList<>();
        for (ReviewEntity reviewEntity : reviewEntityList) {
            reviewDTOList.add(ReviewDTO.toReviewDTO(reviewEntity));
        }
        return reviewDTOList;
    }

    public Page<ReviewDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10;

        Page<ReviewEntity> reviewEntities =
                travelReviewRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        Page<ReviewDTO> reviewDTOS = reviewEntities.map(review ->
                new ReviewDTO(review.getId(), review.getHatesCount(),review.getLikesCount(),review.getMemberEntity().getId(),
                        review.getTitle(),review.getContent(),review.getAddress(),review.getCreatedTime(),
                        review.getMemberEntity().getMemberName(),review.getLatitude(), review.getLongitude(),
                          review.getReview_hits()
                        ));
        return reviewDTOS;
    }
}