package com.WayInto.Travel.Repository.Member;

import com.WayInto.Travel.Entity.Member.MemberChat.ChatReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatReportRepository extends JpaRepository<ChatReportEntity, Long> {

    boolean existsByReporterIdAndChatPartnerId(Long reporterId, Long chatPartnerId);
}
