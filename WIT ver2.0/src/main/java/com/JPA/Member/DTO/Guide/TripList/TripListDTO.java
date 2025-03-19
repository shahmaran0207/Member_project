package com.JPA.Member.DTO.Guide.TripList;

import com.JPA.Member.Entity.Guide.TripList.TripListEntity;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import lombok.ToString;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TripListDTO {

    private Long id;
    private Long guide_id;

    private int likesCount;
    private int price;
    private int hatesCount;
    private int date;
    private int trip_list_hits;

    private String title;
    private String content;
    private String guide_name;
    private String season;

    private LocalDateTime Trip_list_createdTime;

    private List<String> zipcodeList;
    private List<String> trip_list;

    public TripListDTO(Long id, List<String> tripList, String guideName, List<String> zipcodeList,
                       int likesCount, int hatesCount, String title, int tripListHits, String season, String content, int date, int price) {
        this.id = id;
        this.season = season;
        this.trip_list = tripList;
        this.guide_name = guideName;
        this.zipcodeList = zipcodeList;
        this.likesCount = likesCount;
        this.content = content;
        this.hatesCount = hatesCount;
        this.trip_list_hits = tripListHits;
        this.title = title;
        this.date = date;
        this.price = price;
    }

    public static TripListDTO toTripListDTO(TripListEntity tripListEntity) {

        TripListDTO tripListDTO = new TripListDTO();
        tripListDTO.setId(tripListEntity.getId());
        tripListDTO.setDate(tripListEntity.getDate());
        tripListDTO.setHatesCount(0);
        tripListDTO.setLikesCount(0);
        tripListDTO.setTitle(tripListEntity.getTitle());
        tripListDTO.setContent(tripListEntity.getContent());
        tripListDTO.setTrip_list_hits(tripListEntity.getTrip_list_hits());
        tripListDTO.setZipcodeList(tripListEntity.getZipcodeList());
        tripListDTO.setSeason(tripListEntity.getSeason());
        tripListDTO.setDate(tripListEntity.getDate());
        tripListDTO.setPrice(tripListEntity.getPrice());
        tripListDTO.setGuide_id(tripListEntity.getGuideEntity().getId());
        tripListDTO.setGuide_name(tripListEntity.getGuideEntity().getGuideName());

        return tripListDTO;
    }
}
