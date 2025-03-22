package com.WayInto.Travel.Entity.Guide.TripList;

import com.WayInto.Travel.Entity.Member.MemberEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "triplist_hate_table")
public class TripListHateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_list_id", nullable = false)
    private TripListEntity tripListEntity;

    public static TripListHateEntity toSaveEntity(MemberEntity member, TripListEntity tripListEntity) {
        TripListHateEntity tripListHateEntity = new TripListHateEntity();
        tripListHateEntity.setMemberEntity(member);
        tripListHateEntity.setTripListEntity(tripListEntity);
        return tripListHateEntity;
    }
}