package com.WayInto.Travel.DTO.Member;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatReportDTO {

    private Long chatId;
    private Long reporterId;
    private Long partnerId;
}
