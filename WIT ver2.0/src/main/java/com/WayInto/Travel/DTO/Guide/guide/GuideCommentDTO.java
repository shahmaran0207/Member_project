package com.WayInto.Travel.DTO.Guide.guide;

import com.WayInto.Travel.Entity.Guide.Guide.GuideCommentEntity;
import java.time.LocalDateTime;
import lombok.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ToString
public class GuideCommentDTO {

    private Long id;
    private Long commentTargetId;
    private Long memberId;

    private String commentWriter;
    private String commentContents;

    private LocalDateTime commentCreatedTime;

    public static GuideCommentDTO toCommentDTO(GuideCommentEntity commentEntity, Long commentTargetId) {
        GuideCommentDTO commentDTO = new GuideCommentDTO();
        commentDTO.setId(commentEntity.getId());
        commentDTO.setCommentWriter(commentEntity.getCommentWriter());
        commentDTO.setCommentContents(commentEntity.getCommentContents());
        commentDTO.setCommentCreatedTime(commentEntity.getCommentCreatedTime());
        commentDTO.setCommentTargetId(commentTargetId);
        return commentDTO;
    }
}
