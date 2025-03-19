package com.JPA.Member.Repository.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import com.JPA.Member.Entity.Member.MemberTripListEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberTripListRepository extends JpaRepository<MemberTripListEntity, Long> {
    Page<MemberTripListEntity> findByMemberEntityId(Long MemberId, Pageable pageable);

    @Query("select m from MemberTripListEntity m where m.tripListEntity.id = :id")
    Optional<MemberTripListEntity> findFirstByMemberTripListEntityId(@Param("id") Long id);
}