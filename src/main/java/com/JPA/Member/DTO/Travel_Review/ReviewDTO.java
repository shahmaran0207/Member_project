package com.JPA.Member.DTO.Travel_Review;

import org.springframework.web.multipart.MultipartFile;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReviewDTO {

    private int id;
    private int member_id;
    private int review_like;
    private int review_hate;
    private int review_hits;
    private int fileAttached;

    private String title;
    private String content;
    private String reviewpass;
    private String address;
    private String member_name;
    private String originalFileName;
    private String storedFileName;

    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    private float latitude;
    private float longitude;

    private MultipartFile reviewImage;

    public ReviewDTO(int review_hits, int review_hate, int review_like,int id, int member_id, String member_name,String title, String content, String address, LocalDateTime createdTime, LocalDateTime updatedTime, float latitude, float longitude) {
        this.id = id;
        this.review_hate = review_hate;
        this.review_like = review_like;
        this.member_id = member_id;
        this.title = title;
        this.content = content;
        this.address = address;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
        this.member_name = member_name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.review_hits = review_hits;
    }
}