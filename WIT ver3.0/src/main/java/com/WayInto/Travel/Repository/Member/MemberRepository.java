package com.WayInto.Travel.Repository.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import com.WayInto.Travel.Entity.Member.MemberEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findByMemberEmail(String memberEmail);

    Page<MemberEntity> findByMemberArea(String auctionStatus, Pageable pageable);

    Optional<MemberEntity> findByMemberName(String memberName);
}
