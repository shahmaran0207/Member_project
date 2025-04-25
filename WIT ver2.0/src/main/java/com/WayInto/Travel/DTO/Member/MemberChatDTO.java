package com.WayInto.Travel.DTO.Member;

import com.WayInto.Travel.Entity.Member.MemberChat.MemberChatEntity;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberChatDTO {

    private Long id;
    private Long receiver;
    private Long sender;
    private Long chatPartnerId;

    private String senderName;
    private String chatPartnerName;
    private String receiverName;
    private String content;

    private LocalDateTime sentAt;

    private boolean read;

    public static MemberChatDTO fromEntity2(MemberChatEntity chat, Long myId) {
        boolean isSender = chat.getSender().getId().equals(myId);
        return MemberChatDTO.builder()
                .id(chat.getId())
                .sender(chat.getSender().getId())
                .receiver(chat.getReceiver().getId())
                .senderName(chat.getSender().getMemberName())
                .receiverName(chat.getReceiver().getMemberName())
                .chatPartnerId(isSender ? chat.getReceiver().getId() : chat.getSender().getId())
                .chatPartnerName(isSender ? chat.getReceiver().getMemberName() : chat.getSender().getMemberName())
                .content(chat.getContent())
                .sentAt(chat.getSentAt())
                .read(chat.isRead())
                .build();
    }
}
