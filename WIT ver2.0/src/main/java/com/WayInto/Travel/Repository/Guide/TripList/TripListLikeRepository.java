package com.WayInto.Travel.Repository.Guide.TripList;

import com.WayInto.Travel.Entity.Guide.TripList.TripListLikeEntity;
import com.WayInto.Travel.Entity.Guide.TripList.TripListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import com.WayInto.Travel.Entity.Member.MemberEntity;

public interface TripListLikeRepository extends JpaRepository<TripListLikeEntity, Long> {
    boolean existsByMemberEntityAndTripListEntity(MemberEntity member, TripListEntity tripListEntity);
    int countByTripListEntity(TripListEntity tripListEntity);
    void deleteByMemberEntityAndTripListEntity(MemberEntity member, TripListEntity tripListEntity);
}
