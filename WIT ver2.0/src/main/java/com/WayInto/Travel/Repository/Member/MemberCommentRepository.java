package com.WayInto.Travel.Repository.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import com.WayInto.Travel.Entity.Member.MemberCommentEntity;
import com.WayInto.Travel.Entity.Member.MemberEntity;
import java.util.List;

public interface MemberCommentRepository extends JpaRepository<MemberCommentEntity, Long> {
    List<MemberCommentEntity> findAllByCommentTargetOrderByIdDesc(MemberEntity memberEntity);
}
