package com.JPA.Member.DTO.Member;

import com.JPA.Member.Entity.Guide.TripList.TripListEntity;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemberTripListDTO {

    private Long id;
    private Long guide_id;
    private Long member_id;

    private int price;
    private int date;

    private String title;
    private String content;
    private String season;

    private List<String> zipcodeList;
    private List<String> trip_list;

    public MemberTripListDTO(Long id, List<String> tripList, List<String> zipcodeList,
                              String title , String season, String content, int date, int price, Long member_id) {
        this.id = id;
        this.season = season;
        this.trip_list = tripList;
        this.zipcodeList = zipcodeList;
        this.content = content;
        this.title = title;
        this.date = date;
        this.price = price;
        this.member_id = member_id;
    }

    public static MemberTripListDTO toTripListDTO(TripListEntity tripListEntity) {

        MemberTripListDTO tripListDTO = new MemberTripListDTO();
        tripListDTO.setId(tripListEntity.getId());
        tripListDTO.setDate(tripListEntity.getDate());
        tripListDTO.setTitle(tripListEntity.getTitle());
        tripListDTO.setContent(tripListEntity.getContent());
        tripListDTO.setZipcodeList(tripListEntity.getZipcodeList());
        tripListDTO.setSeason(tripListEntity.getSeason());
        tripListDTO.setDate(tripListEntity.getDate());
        tripListDTO.setPrice(tripListEntity.getPrice());
        tripListDTO.setGuide_id(tripListEntity.getGuideEntity().getId());

        return tripListDTO;
    }
}
