package com.WayInto.Travel.Entity.Guide.guide;

import com.WayInto.Travel.DTO.Guide.guide.GuideCommentDTO;
import com.WayInto.Travel.Entity.Member.MemberEntity;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Guide_comment_table")
public class GuideCommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String guidecommentWriter;

    @Column
    private String guidecommentContents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Guidecommenterid")
    private MemberEntity guidecommenter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GuidecommentTargetId")
    private GuideEntity guidecommentTarget;

    @Column
    private LocalDateTime guidecommentCreatedTime;

    public static GuideCommentEntity toSaveEntity(GuideCommentDTO commentDTO, GuideEntity target, MemberEntity memberEntity) {
        GuideCommentEntity commentEntity = new GuideCommentEntity();
        commentEntity.setGuidecommentWriter(commentDTO.getGuidecommentWriter());
        commentEntity.setGuidecommentContents(commentDTO.getGuidecommentContents());
        commentEntity.setGuidecommentTarget(target);
        commentEntity.setGuidecommentCreatedTime(LocalDateTime.now());
        commentEntity.setGuidecommenter(memberEntity);
        return commentEntity;
    }
}
