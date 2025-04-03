package com.WayInto.Travel.Service.Travel_Review;

import com.WayInto.Travel.Repository.Travel_Review.TravelReviewFileRepository;
import com.WayInto.Travel.Repository.Travel_Review.TravelReviewHateRepository;
import com.WayInto.Travel.Repository.Travel_Review.TravelReviewLikeRepository;
import com.WayInto.Travel.Repository.Travel_Review.Travel_ReviewRepository;
import com.WayInto.Travel.Entity.Travel_Review.ReviewFileEntity;
import org.springframework.transaction.annotation.Transactional;
import com.WayInto.Travel.Repository.Member.MemberRepository;
import com.WayInto.Travel.Entity.Travel_Review.ReviewEntity;
import org.springframework.web.multipart.MultipartFile;
import com.WayInto.Travel.DTO.Travel_Review.ReviewDTO;
import com.WayInto.Travel.Entity.Member.MemberEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.WayInto.Travel.Service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import lombok.RequiredArgsConstructor;
import java.io.IOException;
import java.util.Optional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TravelReviewService {

    private final ImageService imageService;
    private final MemberRepository memberRepository;
    private final Travel_ReviewRepository travelReviewRepository;
    private final TravelReviewFileRepository travelReviewFileRepository;
    private final Travel_ReviewRepository travel_ReviewRepository;
    private final TravelReviewHateRepository travelReviewHateRepository;
    private final TravelReviewLikeRepository travelReviewLikeRepository;

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

    public void save(ReviewDTO reviewDTO, Long id) throws IOException {
        MemberEntity memberEntity = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + id));

        MultipartFile reviewImage = reviewDTO.getReviewImage();
        if (reviewImage == null || reviewImage.isEmpty()) {
            ReviewEntity reviewEntity = ReviewEntity.toSaveEntity(reviewDTO, memberEntity);
            travelReviewRepository.save(reviewEntity);
        } else {
            MultipartFile reviewUploadImage = reviewDTO.getReviewImage();

            String s3Url = imageService.imageUploadFromFile(reviewUploadImage);

            String originalFilename = reviewUploadImage.getOriginalFilename();
            String storedFileName = System.currentTimeMillis() + "_" + originalFilename;

            ReviewEntity reviewEntity = ReviewEntity.toSaveFileEntity(reviewDTO, memberEntity);
            Long savedId = travelReviewRepository.save(reviewEntity).getId();
            ReviewEntity savedReviewEntity = travelReviewRepository.findById(savedId).get();

            ReviewFileEntity reviewFileEntity = ReviewFileEntity.toReviewFileEntity(savedReviewEntity, storedFileName, s3Url);
            travelReviewFileRepository.save(reviewFileEntity);
        }
    }

    @Transactional
    public void updateHits(Long id) {
        travelReviewRepository.updateHits(id);
    }

    @Transactional
    public ReviewDTO findById(Long id) {
        Optional<ReviewEntity> optionalReviewEntity = travelReviewRepository.findById(id);
        if (optionalReviewEntity.isPresent()) {
            ReviewEntity reviewEntity = optionalReviewEntity.get();
            return ReviewDTO.toReviewDTO(reviewEntity);
        } else {
            return null;
        }
    }

    @Transactional
    public void delete(Long id) {
        List<ReviewFileEntity> reviewFiles = travelReviewFileRepository.findByReviewEntity_Id(id);

        if(reviewFiles.isEmpty()) {
            travel_ReviewRepository.deleteById(id);
        } else {
            for (ReviewFileEntity file : reviewFiles) {
                imageService.deleteImage(file.getStoredFileName());
                travelReviewFileRepository.delete(file);
            }

            travelReviewHateRepository.deleteByReviewEntity_Id(id);
            travelReviewLikeRepository.deleteByReviewEntity_Id(id);
            travel_ReviewRepository.deleteById(id);
        }
    }
}
