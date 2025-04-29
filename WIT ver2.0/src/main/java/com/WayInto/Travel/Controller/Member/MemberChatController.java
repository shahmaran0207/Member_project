package com.WayInto.Travel.Controller.Member;

import com.WayInto.Travel.Controller.ControllerAdvice.GlobalControllerAdvice;
import com.WayInto.Travel.Repository.Member.ChatReportRepository;
import com.WayInto.Travel.Repository.Member.MemberRepository;
import com.WayInto.Travel.Service.Member.ChatReportService;
import com.WayInto.Travel.Service.Member.MemberChatService;
import com.WayInto.Travel.Entity.Member.MemberEntity;
import com.WayInto.Travel.DTO.Member.MemberChatDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class MemberChatController {

    private final ChatReportRepository chatReportRepository;
    private final ChatReportService chatReportService;
    private final MemberRepository memberRepository;
    private final MemberChatService memberChatService;
    private final GlobalControllerAdvice globalControllerAdvice;
    private final MemberChatService chatService;

    @GetMapping("/list")
    public String chatRoomList(Model model, HttpServletRequest request) {

        String loginId = globalControllerAdvice.getCookieValue(request, "loginId");
        Long memberId = (loginId != null) ? Long.valueOf(loginId) : null;

        List<MemberChatDTO> chatRoomList = chatService.getMyChatRooms(memberId);

        model.addAttribute("chatRoomList", chatRoomList);
        return "Member/MemberChatList";
    }

    @PostMapping("/send")
    @ResponseBody
    public ResponseEntity<?> sendChat(@RequestBody MemberChatDTO memberChatDTO) {
        chatService.save(memberChatDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/room/{memberId}")
    public String chatRoom(@PathVariable Long memberId, Model model, HttpServletRequest request) {

        String loginId = globalControllerAdvice.getCookieValue(request, "loginId");
        Long member = (loginId != null) ? Long.valueOf(loginId) : null;

        MemberEntity sender = memberRepository.findById(member).orElseThrow();
        MemberEntity receiver = memberRepository.findById(memberId).orElseThrow();

        memberChatService.markMessagesAsRead(sender, receiver);

        List<MemberChatDTO> chatList = memberChatService.getChatBetween(sender, receiver);
        model.addAttribute("loginId", loginId);
        model.addAttribute("chatList", chatList);
        model.addAttribute("receiver", receiver);
        model.addAttribute("sender", sender);

        return "Member/ChatRoom";
    }

    @PostMapping("/request")
    @ResponseBody
    public Map<String, Object> requestChat(@RequestBody Map<String, Long> request) {
        Long fromMemberId = request.get("fromMemberId");
        Long toMemberId = request.get("toMemberId");

        if (fromMemberId.equals(toMemberId)) {
            throw new IllegalArgumentException("본인에게는 채팅을 신청할 수 없습니다.");
        }

        MemberEntity sender = memberRepository.findById(fromMemberId).orElseThrow();
        MemberEntity receiver = memberRepository.findById(toMemberId).orElseThrow();

        Map<String, Object> response = new HashMap<>();
        response.put("roomId", toMemberId);
        return response;
    }

    @PostMapping("/report")
    public ResponseEntity<?> reportChat(@RequestBody Map<String, Object> request) {
        Long chatId = Long.valueOf(request.get("chatId").toString());
        Long reporterId = Long.valueOf(request.get("reporterId").toString());
        Long partnerId = Long.valueOf(request.get("partnerId").toString());

        chatReportService.reportChat(chatId, reporterId, partnerId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/blocked")
    public ResponseEntity<Boolean> isChatBlocked(
            @RequestParam Long senderId,
            @RequestParam Long receiverId) {

        boolean blocked = chatReportRepository.existsByReporterIdAndChatPartnerId(senderId, receiverId) ||
                chatReportRepository.existsByReporterIdAndChatPartnerId(receiverId, senderId);

        return ResponseEntity.ok(blocked);
    }

}