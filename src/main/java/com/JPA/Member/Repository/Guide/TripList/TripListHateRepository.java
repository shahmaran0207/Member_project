package com.JPA.Member.Repository.Guide.TripList;

import com.JPA.Member.Entity.Guide.TripList.TripListHateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import com.JPA.Member.Entity.Guide.TripList.TripListEntity;
import com.JPA.Member.Entity.Member.MemberEntity;

public interface TripListHateRepository extends JpaRepository<TripListHateEntity, Long> {
    boolean existsByMemberEntityAndTripListEntity(MemberEntity member, TripListEntity tripListEntity);
    int countByTripListEntity(TripListEntity tripListEntity);
    void deleteByMemberEntityAndTripListEntity(MemberEntity member, TripListEntity tripListEntity);
}
