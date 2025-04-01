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
@Table(name = "Guide_comment_table")
public class GuideCommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String GuidecommentWriter;

    @Column
    private String GuidecommentContents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Guidecommenterid")
    private MemberEntity Guidecommenter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GuidecommentTargetId")
    private GuideEntity GuidecommentTarget;

    @Column
    private LocalDateTime GuidecommentCreatedTime;

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
