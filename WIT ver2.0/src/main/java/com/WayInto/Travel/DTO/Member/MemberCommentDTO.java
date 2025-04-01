package com.WayInto.Travel.DTO.Member;

import com.WayInto.Travel.Entity.Member.MemberCommentEntity;
import java.time.LocalDateTime;
import lombok.ToString;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
@ToString
public class MemberCommentDTO {

    private Long id;
    private Long commentTargetId;
    private Long memberId;

    private String commentWriter;
    private String commentContents;

    private LocalDateTime commentCreatedTime;

    public static MemberCommentDTO toCommentDTO(MemberCommentEntity commentEntity, Long commentTargetId) {
        MemberCommentDTO commentDTO = new MemberCommentDTO();
        commentDTO.setId(commentEntity.getId());
        commentDTO.setCommentWriter(commentEntity.getCommentWriter());
        commentDTO.setCommentContents(commentEntity.getCommentContents());
        commentDTO.setCommentCreatedTime(commentEntity.getCommentCreatedTime());
        commentDTO.setCommentTargetId(commentTargetId);
        return commentDTO;
    }
}