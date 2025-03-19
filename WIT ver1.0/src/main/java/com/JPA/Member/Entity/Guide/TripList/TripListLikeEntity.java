package com.JPA.Member.Entity.Guide.TripList;

import com.JPA.Member.Entity.Member.MemberEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "triplist_like_table")
public class TripListLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_list_id", nullable = false)
    private TripListEntity tripListEntity;

    public static TripListLikeEntity toSaveEntity(MemberEntity member, TripListEntity triplist) {
        TripListLikeEntity tripListLike = new TripListLikeEntity();
        tripListLike.setMemberEntity(member);
        tripListLike.setTripListEntity(triplist);
        return tripListLike;
    }
}
