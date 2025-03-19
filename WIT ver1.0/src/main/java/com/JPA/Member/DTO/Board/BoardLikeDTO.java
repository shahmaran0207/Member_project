package com.JPA.Member.DTO.Board;

import lombok.Data;

@Data
public class BoardLikeDTO {
    private Long id;
    private Long memberId;
    private Long boardId;
}
