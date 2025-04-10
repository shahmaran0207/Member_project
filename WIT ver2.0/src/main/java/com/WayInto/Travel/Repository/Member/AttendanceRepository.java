package com.WayInto.Travel.Repository.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import com.WayInto.Travel.Entity.Member.AttendanceEntity;
import org.springframework.data.repository.query.Param;
import com.WayInto.Travel.Entity.Member.MemberEntity;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Long> {

    boolean existsByMemberAndAttendanceDate(MemberEntity member, LocalDate attendanceDate);

    @Query("SELECT a.attendanceDate FROM AttendanceEntity a WHERE a.member.id = :memberId AND YEAR(a.attendanceDate) = :year AND MONTH(a.attendanceDate) = :month")
    List<LocalDate> findAttendanceByMemberAndMonth(@Param("memberId") Long memberId, @Param("year") int year, @Param("month") int month);

    boolean existsByMemberIdAndAttendanceDate(Long memberId, LocalDate today);
}