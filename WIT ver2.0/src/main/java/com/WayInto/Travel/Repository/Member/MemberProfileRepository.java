package com.WayInto.Travel.Repository.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import com.WayInto.Travel.Entity.Member.MemberProfileEntity;
import com.WayInto.Travel.Entity.Member.MemberEntity;
import java.util.List;

public interface MemberProfileRepository extends JpaRepository<MemberProfileEntity, Long> {
    List<MemberProfileEntity> findByMemberEntity(MemberEntity exisitingMemberEntity);
}
