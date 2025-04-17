package com.WayInto.Travel.Repository.Member;

import com.WayInto.Travel.Entity.Member.MemberTripListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java.util.Optional;

public interface MemberTripListRepository extends JpaRepository<MemberTripListEntity, Long> {
    Page<MemberTripListEntity> findByMemberEntity_Id(Long memberId, Pageable pageable);

    @Query("select m from MemberTripListEntity m where m.tripListEntity.id = :id")
    Optional<MemberTripListEntity> findFirstByMemberTripListEntityId(@Param("id") Long id);
}