package com.WayInto.Travel.Repository.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import com.WayInto.Travel.Entity.Member.MemberHateEntity;
import com.WayInto.Travel.Entity.Member.MemberEntity;

public interface MemberHateRepository extends JpaRepository<MemberHateEntity, Long> {
    boolean existsByHaterAndTargetHater(MemberEntity hater, MemberEntity target_hater);

    void deleteByHaterAndTargetHater(MemberEntity hater, MemberEntity target_hater);

    int countByTargetHater(MemberEntity target_hater);
}
