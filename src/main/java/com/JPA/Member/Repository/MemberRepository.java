package com.JPA.Member.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.JPA.Member.Entity.MemberEntity;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByMemberEmail(String memberEmail);
}