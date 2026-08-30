package com.WayInto.Travel.Controller.Member;

import com.WayInto.Travel.Controller.ControllerAdvice.GlobalControllerAdvice;
import com.WayInto.Travel.Service.Member.AttendanceService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final GlobalControllerAdvice globalControllerAdvice;
    private final AttendanceService attendanceService;

    @GetMapping
    public ResponseEntity<List<LocalDate>> getAttendanceRecords(@RequestParam Long memberId, @RequestParam int year, @RequestParam int month) {
        List<LocalDate> attendanceDates = attendanceService.getAttendanceRecords(memberId, year, month);
        return ResponseEntity.ok(attendanceDates);
    }

    @PostMapping("/check")
    public ResponseEntity<Map<String, String>> markAttendance(HttpServletRequest request) {
        String loginId = globalControllerAdvice.getCookieValue(request, "loginId");
        Long memberId = (loginId != null) ? Long.valueOf(loginId) : null;

        boolean success = attendanceService.markAttendance(memberId);

        Map<String, String> response = new HashMap<>();

        if (success) {
            response.put("msg", "출석 체크 완료!");
        } else {
            response.put("msg", "이미 출석 체크를 완료했습니다.");
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Boolean>> getAttendanceStatus(HttpServletRequest request) {
        String loginId = globalControllerAdvice.getCookieValue(request, "loginId");
        Long memberId = (loginId != null) ? Long.valueOf(loginId) : null;

        boolean hasAttended = attendanceService.hasAttendedToday(memberId);

        Map<String, Boolean> response = new HashMap<>();
        response.put("attended", hasAttended);
        return ResponseEntity.ok(response);
    }

}