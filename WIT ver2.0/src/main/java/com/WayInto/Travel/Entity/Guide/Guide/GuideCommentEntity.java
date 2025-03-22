package com.WayInto.Travel.Entity.Guide.Guide;

import com.WayInto.Travel.DTO.Guide.guide.GuideCommentDTO;
import com.WayInto.Travel.Entity.Member.MemberEntity;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "member_comment_table")
public class GuideCommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String commentWriter;

    @Column
    private String commentContents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commenterid")
    private MemberEntity commenter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commentTargetId")
    private GuideEntity commentTarget;

    @Column
    private LocalDateTime commentCreatedTime;

    public static GuideCommentEntity toSaveEntity(GuideCommentDTO commentDTO, GuideEntity target, MemberEntity memberEntity) {
        GuideCommentEntity commentEntity = new GuideCommentEntity();
        commentEntity.setCommentWriter(commentDTO.getCommentWriter());
        commentEntity.setCommentContents(commentDTO.getCommentContents());
        commentEntity.setCommentTarget(target);
        commentEntity.setCommentCreatedTime(LocalDateTime.now());
        commentEntity.setCommenter(memberEntity);
        return commentEntity;
    }
}
