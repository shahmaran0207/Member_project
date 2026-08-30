package com.WayInto.Travel.Entity.Member.MemberChat;

import com.WayInto.Travel.Entity.Member.MemberEntity;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "member_chat_table")
public class MemberChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private MemberEntity sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private MemberEntity receiver;

    @Column(nullable = false)
    private String content;

    private LocalDateTime sentAt;

    @Column(name = "`read`")
    private boolean read;

    @PrePersist
    public void prePersist() {
        this.sentAt = LocalDateTime.now();
        this.read = false;
    }

}