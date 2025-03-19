package com.JPA.Member.Entity.Guide.TripList;

import com.JPA.Member.DTO.Guide.TripList.TripListDTO;
import com.JPA.Member.Entity.Guide.Guide.GuideEntity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="trip_list_table")
public class TripListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private int likesCount = 0;

    @Column
    private int hatesCount = 0;

    @Column
    private String content;

    @Column
    private int date;

    @Column
    private int price;

    @Column
    private int trip_list_hits;

    @Column
    private String season;

    @Column
    @ElementCollection
    private List<String> zipcodeList = new ArrayList<>();

    @Column
    @ElementCollection
    private List<String> trip_list = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guide_id", nullable = false)
    private GuideEntity guideEntity;

    public void increaseLikesCount() {
        this.likesCount++;
    }

    public void decreaseLikesCount() {
        if (this.likesCount > 0) {
            this.likesCount--;
        }
    }

    public void increaseHatesCount() {
        this.hatesCount++;
    }

    public void decreaseHatesCount() {
        if (this.hatesCount > 0) {
            this.hatesCount--;
        }
    }

    public static TripListEntity toSaveEntity(TripListDTO tripListDTO, GuideEntity guideEntity) {
        TripListEntity tripListEntity = new TripListEntity();
        tripListEntity.setId(tripListDTO.getId());
        tripListEntity.setTitle(tripListDTO.getTitle());
        tripListEntity.setPrice(tripListDTO.getPrice());
        tripListEntity.setSeason(tripListDTO.getSeason());
        tripListEntity.setZipcodeList(tripListDTO.getZipcodeList());
        tripListEntity.setTrip_list_hits(0);
        tripListEntity.setDate(tripListDTO.getDate());
        tripListEntity.setLikesCount(0);
        tripListEntity.setHatesCount(0);
        tripListEntity.setContent(tripListDTO.getContent());
        tripListEntity.setGuideEntity(guideEntity);
        return tripListEntity;
    }
}