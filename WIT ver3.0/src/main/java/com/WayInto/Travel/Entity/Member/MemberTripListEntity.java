package com.WayInto.Travel.Entity.Member;

import com.WayInto.Travel.Entity.Guide.TripList.TripListEntity;
import com.WayInto.Travel.DTO.Guide.TripList.TripListDTO;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="member_trip_list_table")
public class MemberTripListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

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
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_list_id", nullable = false)
    private TripListEntity tripListEntity;

    public static MemberTripListEntity toSaveEntity(TripListDTO tripListDTO, MemberEntity memberEntity, TripListEntity tripListEntity) {

        MemberTripListEntity memberTripListEntity = new MemberTripListEntity();
        memberTripListEntity.setTitle(tripListDTO.getTitle());
        memberTripListEntity.setPrice(tripListDTO.getPrice());
        memberTripListEntity.setSeason(tripListDTO.getSeason());
        memberTripListEntity.setZipcodeList(tripListDTO.getZipcodeList());
        memberTripListEntity.setDate(tripListDTO.getDate());
        memberTripListEntity.setContent(tripListDTO.getContent());
        memberTripListEntity.setMemberEntity(memberEntity);
        memberTripListEntity.setTripListEntity(tripListEntity);
        return memberTripListEntity;
    }

}