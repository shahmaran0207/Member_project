package com.JPA.Member.DTO.Guide.TripList;

import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.time.LocalDate;
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
    private int hatesCount;
    private int trip_list_hits;

    private String title;
    private String guide_name;

    private LocalDateTime Trip_list_createdTime;
    private LocalDate startDate;
    private LocalDate endDate;

    private List<String> zipcodeList;
    private List<String> trip_list;

    public TripListDTO(Long id, List<String> tripList, LocalDate endDate, LocalDate startDate, String guideName, List<String> zipcodeList,
                       int likesCount, int hatesCount, String title, int tripListHits) {
        this.id = id;
        this.trip_list = tripList;
        this.endDate = endDate;
        this.startDate = startDate;
        this.guide_name = guideName;
        this.zipcodeList = zipcodeList;
        this.likesCount = likesCount;
        this.hatesCount = hatesCount;
        this.trip_list_hits = tripListHits;
        this.title = title;
    }
}
