package com.WayInto.Travel.Repository.Member;

import com.WayInto.Travel.Entity.Member.MemberChat.MemberChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import com.WayInto.Travel.Entity.Member.MemberEntity;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface MemberChatRepository extends JpaRepository<MemberChatEntity, Long> {
    @Query("SELECT c FROM MemberChatEntity c WHERE " +
            "(c.sender = :sender AND c.receiver = :receiver) OR " +
            "(c.sender = :receiver AND c.receiver = :sender) " +
            "ORDER BY c.sentAt ASC")
    List<MemberChatEntity> findBySenderAndReceiverOrReceiverAndSender(
            @Param("sender") MemberEntity sender,
            @Param("receiver") MemberEntity receiver
    );

    List<MemberChatEntity> findBySenderAndReceiverAndReadIsFalse(MemberEntity sender, MemberEntity receiver);

    @Query("""
    SELECT c FROM MemberChatEntity c
    WHERE c.sender.id = :memberId OR c.receiver.id = :memberId
    ORDER BY c.sentAt DESC
""")
    List<MemberChatEntity> findAllByMemberId(@Param("memberId") Long memberId);
}