package com.JPA.Member.DTO.Travel_Review;

import com.JPA.Member.Entity.Travel_Review.ReviewEntity;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class ReviewDTO {

    private Long id;
    private Long member_id;

    private int likesCount;
    private int hatesCount;
    private int review_hits;
    private int fileAttached;

    private String title;
    private String content;
    private String reviewpass;
    private String address;
    private String member_name;
    private String originalFileName;
    private String storedFileName;

    private LocalDateTime Review_createdTime;
    private LocalDateTime Review_updatedTime;
    private LocalDate startDate;
    private LocalDate endDate;

    private MultipartFile reviewImage;

    private List<String> zipcodeList;

    public ReviewDTO(Long id,int hatesCount, int likesCount,
                     Long member_id, String title, String content, String address,
                     LocalDateTime Review_createdTime, String member_name, int review_hits, List<String> zipcodeList, LocalDate startDate, LocalDate endDate)
    {
        this.id = id;
        this.likesCount = likesCount;
        this.member_id = member_id;
        this.title = title;
        this.content = content;
        this.address = address;
        this.Review_createdTime = Review_createdTime;
        this.member_name = member_name;
        this.hatesCount = hatesCount;
        this.review_hits = review_hits;
        this.zipcodeList = zipcodeList;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    public static ReviewDTO toReviewDTO(ReviewEntity reviewEntity) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(reviewEntity.getId());
        reviewDTO.setReviewpass(reviewEntity.getReviewpass());
        reviewDTO.setTitle(reviewEntity.getTitle());
        reviewDTO.setContent(reviewEntity.getContent());
        reviewDTO.setReview_hits(reviewEntity.getReview_hits());
        reviewDTO.setReview_createdTime(reviewEntity.getCreatedTime());
        reviewDTO.setReview_updatedTime(reviewEntity.getUpdatedTime());
        reviewDTO.setLikesCount(0);
        reviewDTO.setHatesCount(0);
        reviewDTO.setZipcodeList(reviewEntity.getZipcodeList());
        reviewDTO.setStartDate(reviewEntity.getStartDate());
        reviewDTO.setEndDate(reviewEntity.getEndDate());
        reviewDTO.setMember_name(reviewEntity.getMemberEntity().getMemberName());

        if(reviewEntity.getFileAttached()==0){
            reviewDTO.setFileAttached(reviewEntity.getFileAttached());
        } else{
            reviewDTO.setFileAttached(reviewEntity.getFileAttached());
            reviewDTO.setOriginalFileName(reviewEntity.getReviewFileEntityList().get(0).getOriginalFileName());
            reviewDTO.setStoredFileName(reviewEntity.getReviewFileEntityList().get(0).getStoredFileName());
        }

        return reviewDTO;
    }
}