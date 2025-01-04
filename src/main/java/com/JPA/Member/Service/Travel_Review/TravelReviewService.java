package com.JPA.Member.Service.Travel_Review;

import com.JPA.Member.Repository.Travel_Review.TravelReviewFileRepository;
import com.JPA.Member.Repository.Travel_Review.TravelReviewRepository;
import com.JPA.Member.Entity.Travel_Review.ReviewFileEntity;
import com.JPA.Member.Repository.Member.MemberRepository;
import com.JPA.Member.Entity.Travel_Review.ReviewEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.PageRequest;
import com.JPA.Member.DTO.Travel_Review.ReviewDTO;
import com.JPA.Member.Entity.Member.MemberEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import java.io.IOException;
import java.io.File;

@RequiredArgsConstructor
@Service
public class TravelReviewService {
    private final TravelReviewRepository travelReviewRepository;
    private final MemberRepository memberRepository;
    private final TravelReviewFileRepository travelReviewFileRepository;

    public Page<ReviewDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10;

        Page<ReviewEntity> reviewEntities =
                travelReviewRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        Page<ReviewDTO> reviewDTOS = reviewEntities.map(review ->
                new ReviewDTO(review.getId(), review.getHatesCount(),review.getLikesCount(),review.getMemberEntity().getId(),
                        review.getTitle(),review.getContent(),review.getAddress(),review.getCreatedTime(),
                        review.getMemberEntity().getMemberName(), review.getReview_hits(), review.getZipcodeList(), review.getStartDate(), review.getEndDate()
                        ));
        return reviewDTOS;
    }

    public void save(ReviewDTO reviewDTO, Long id) throws IOException {
        MemberEntity memberEntity = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + id));

        // null 체크 추가
        MultipartFile reviewImage = reviewDTO.getReviewImage();
        if (reviewImage == null || reviewImage.isEmpty()) {
            ReviewEntity reviewEntity = ReviewEntity.toSaveEntity(reviewDTO, memberEntity);
            travelReviewRepository.save(reviewEntity);
        } else {
            String originalFilename = reviewImage.getOriginalFilename();
            String storedFileName = System.currentTimeMillis() + "_" + originalFilename;
            String savePath = "C:/Users/wjaud/OneDrive/바탕 화면/MOST IMPORTANT/Member_project/file/" + storedFileName;
            reviewImage.transferTo(new File(savePath));

            ReviewEntity reviewEntity = ReviewEntity.toSaveFileEntity(reviewDTO, memberEntity);
            Long savedId = travelReviewRepository.save(reviewEntity).getId();

            ReviewEntity savedReviewEntity = travelReviewRepository.findById(savedId).get();

            ReviewFileEntity reviewFileEntity = ReviewFileEntity.toReviewFileEntity(savedReviewEntity, originalFilename, storedFileName);
            travelReviewFileRepository.save(reviewFileEntity);
        }
    }

    @Transactional
    public void updateHits(Long id) {
        travelReviewRepository.updateHits(id);
    }

}