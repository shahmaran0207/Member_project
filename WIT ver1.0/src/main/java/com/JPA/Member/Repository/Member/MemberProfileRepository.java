package com.JPA.Member.Repository.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import com.JPA.Member.Entity.Member.MemberProfileEntity;

public interface MemberProfileRepository extends JpaRepository<MemberProfileEntity, Long> {
}
