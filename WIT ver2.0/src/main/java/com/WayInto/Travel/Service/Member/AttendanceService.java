package com.WayInto.Travel.Service.Member;

import com.WayInto.Travel.Repository.Member.AttendanceRepository;
import org.springframework.transaction.annotation.Transactional;
import com.WayInto.Travel.Repository.Member.MemberRepository;
import com.WayInto.Travel.Entity.Member.AttendanceEntity;
import com.WayInto.Travel.Entity.Member.MemberEntity;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final MemberRepository memberRepository;

    // 특정 회원의 출석 기록 조회
    public List<LocalDate> getAttendanceRecords(Long memberId, int year, int month) {
        return attendanceRepository.findAttendanceByMemberAndMonth(memberId, year, month);
    }

    // 출석 체크 처리
    @Transactional
    public boolean markAttendance(Long memberId) {
        MemberEntity member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("회원 정보 없음"));

        LocalDate today = LocalDate.now();

        // 이미 출석했는지 확인
        boolean alreadyChecked = attendanceRepository.existsByMemberAndAttendanceDate(member, today);
        if (alreadyChecked) {
            return false;
        }

        // 출석 체크 저장
        AttendanceEntity attendance = new AttendanceEntity();
        attendance.setMember(member);
        attendance.setAttendanceDate(today);
        attendanceRepository.save(attendance);

        // 총 출석 횟수 증가
        member.increaseTotalAttendance();
        member.setMemberMoney(member.getMemberMoney() + 10); // 출석 체크 시 10포인트 추가
        memberRepository.save(member);

        return true;
    }

    public boolean hasAttendedToday(Long memberId) {
        if (memberId == null) return false;

        LocalDate today = LocalDate.now();
        return attendanceRepository.existsByMemberIdAndAttendanceDate(memberId, today);
    }

}
