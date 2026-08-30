package com.WayInto.Travel.Service.Member;

import com.WayInto.Travel.Entity.Member.MemberChat.ChatReportEntity;
import com.WayInto.Travel.Entity.Member.MemberChat.MemberChatEntity;
import com.WayInto.Travel.Repository.Member.ChatReportRepository;
import com.WayInto.Travel.Repository.Member.MemberChatRepository;
import com.WayInto.Travel.Repository.Member.MemberRepository;
import com.WayInto.Travel.Entity.Member.MemberEntity;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatReportService {

    private final MemberRepository memberRepository;
    private final MemberChatRepository memberChatRepository;
    private final ChatReportRepository chatReportRepository;

    public void reportChat(Long chatId, Long reporterId, Long partnerId) {
        MemberChatEntity chat = memberChatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("채팅이 존재하지 않습니다."));

        MemberEntity reporter = memberRepository.findById(reporterId)
                .orElseThrow(() -> new RuntimeException("신고자 정보가 없습니다."));

        MemberEntity partner = memberRepository.findById(partnerId)
                .orElseThrow(() -> new RuntimeException("상대방 정보가 없습니다."));

        ChatReportEntity report = new ChatReportEntity();
        report.setChat(chat);
        report.setReporter(reporter);
        report.setChatPartner(partner);

        chatReportRepository.save(report);
    }
}
