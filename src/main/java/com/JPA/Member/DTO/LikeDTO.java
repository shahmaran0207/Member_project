package com.JPA.Member.DTO;

import lombok.Data;

@Data
public class LikeDTO {
    private Long id;
    private Long memberId;
    private Long boardId;
}
