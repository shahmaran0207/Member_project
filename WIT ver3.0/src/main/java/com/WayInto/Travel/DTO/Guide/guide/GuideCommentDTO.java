package com.WayInto.Travel.DTO.Guide.guide;

import com.WayInto.Travel.Entity.Guide.guide.GuideCommentEntity;
import java.time.LocalDateTime;
import lombok.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ToString
public class GuideCommentDTO {

    private Long id;
    private Long guidecommentTargetId;
    private Long memberId;

    private String guidecommentWriter;
    private String guidecommentContents;

    private LocalDateTime guidecommentCreatedTime;

    public static GuideCommentDTO toCommentDTO(GuideCommentEntity commentEntity, Long commentTargetId) {
        GuideCommentDTO commentDTO = new GuideCommentDTO();
        commentDTO.setId(commentEntity.getId());
        commentDTO.setGuidecommentWriter(commentEntity.getGuidecommentWriter());
        commentDTO.setGuidecommentContents(commentEntity.getGuidecommentContents());
        commentDTO.setGuidecommentCreatedTime(commentEntity.getGuidecommentCreatedTime());
        commentDTO.setGuidecommentTargetId(commentTargetId);
        return commentDTO;
    }
}
