package com.WayInto.Travel.Service.Member;

import com.WayInto.Travel.Entity.Member.MemberChat.MemberChatEntity;
import com.WayInto.Travel.Repository.Member.MemberChatRepository;
import org.springframework.transaction.annotation.Transactional;
import com.WayInto.Travel.Repository.Member.MemberRepository;
import com.WayInto.Travel.Entity.Member.MemberEntity;
import com.WayInto.Travel.DTO.Member.MemberChatDTO;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MemberChatService {

    private final MemberChatRepository memberChatRepository;
    private final MemberRepository memberRepository;

    public List<MemberChatDTO> getChatBetween(MemberEntity sender, MemberEntity receiver) {
        Long myId = sender.getId();
        List<MemberChatEntity> chats = memberChatRepository.findBySenderAndReceiverOrReceiverAndSender(sender, receiver);
        return chats.stream()
                .map(chat -> MemberChatDTO.fromEntity2(chat, myId))
                .collect(Collectors.toList());
    }

    public void save(MemberChatDTO chatDTO) {
        MemberEntity sender = memberRepository.findById(chatDTO.getSender())
                .orElseThrow(() -> new IllegalArgumentException("보낸 사람 정보를 찾을 수 없습니다."));
        MemberEntity receiver = memberRepository.findById(chatDTO.getReceiver())
                .orElseThrow(() -> new IllegalArgumentException("받는 사람 정보를 찾을 수 없습니다."));

        MemberChatEntity chatEntity = MemberChatEntity.builder()
                .sender(sender)
                .receiver(receiver)
                .content(chatDTO.getContent())
                .sentAt(LocalDateTime.now())
                .read(false)
                .build();

        memberChatRepository.save(chatEntity);
    }

    @Transactional
    public void markMessagesAsRead(MemberEntity reader, MemberEntity sender) {
        List<MemberChatEntity> unreadMessages = memberChatRepository.findBySenderAndReceiverAndReadIsFalse(sender, reader);

        for (MemberChatEntity chat : unreadMessages) {
            chat.setRead(true);
        }

        memberChatRepository.saveAll(unreadMessages);
    }

    public List<MemberChatDTO> getMyChatRooms(Long memberId) {
        MemberEntity me = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

        List<MemberChatEntity> allChats = memberChatRepository.findAllByMemberId(memberId);

        Set<Long> seen = new HashSet<>();

        return allChats.stream()
                .filter(chat -> {
                    Long partnerId = chat.getSender().getId().equals(memberId)
                            ? chat.getReceiver().getId()
                            : chat.getSender().getId();

                    return seen.add(partnerId);
                })
                .map(chat -> MemberChatDTO.fromEntity2(chat, memberId))

                .collect(Collectors.toList());
    }

}
