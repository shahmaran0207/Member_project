package com.JPA.Member.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "like_table")
public class LikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private BoardEntity boardEntity;

    public static LikeEntity toSaveEntity(MemberEntity member, BoardEntity board) {
        LikeEntity likeEntity = new LikeEntity();
        likeEntity.setMemberEntity(member);
        likeEntity.setBoardEntity(board);
        return likeEntity;
    }
}
