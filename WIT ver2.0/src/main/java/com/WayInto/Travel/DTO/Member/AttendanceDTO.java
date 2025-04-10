package com.WayInto.Travel.DTO.Member;

import com.WayInto.Travel.Entity.Member.AttendanceEntity;
import com.WayInto.Travel.Entity.Member.MemberEntity;
import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AttendanceDTO {
    private Long id;
    private Long memberId;
    private LocalDate attendanceDate;

    public static AttendanceDTO toAttendanceDTO(AttendanceEntity attendanceEntity) {
        AttendanceDTO attendanceDTO = new AttendanceDTO();
        attendanceDTO.setId(attendanceEntity.getId());
        attendanceDTO.setMemberId(attendanceEntity.getMember().getId());
        attendanceDTO.setAttendanceDate(attendanceEntity.getAttendanceDate());
        return attendanceDTO;
    }

    public static AttendanceEntity toEntity(AttendanceDTO attendanceDTO, MemberEntity memberEntity) {
        AttendanceEntity attendanceEntity = new AttendanceEntity();
        attendanceEntity.setId(attendanceDTO.getId());
        attendanceEntity.setMember(memberEntity);
        attendanceEntity.setAttendanceDate(attendanceDTO.getAttendanceDate());
        return attendanceEntity;
    }
}