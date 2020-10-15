package com.yuditsky.classroom.controller.v2;

import com.yuditsky.classroom.exception.AccessDeniedException;
import com.yuditsky.classroom.model.Report;
import com.yuditsky.classroom.security.JwtTokenProvider;
import com.yuditsky.classroom.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v2/report")
public class ReportController {

    private final ReportService reportService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public ReportController(ReportService reportService, JwtTokenProvider jwtTokenProvider) {
        this.reportService = reportService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('TEACHER')")
    public ResponseEntity<?> save(@RequestBody Report report) {
        return new ResponseEntity<>(reportService.save(report), HttpStatus.OK);
    }

    @DeleteMapping()
    @PreAuthorize("hasAuthority('TEACHER')")
    public void remove(@RequestHeader("Authorization") String token, @RequestBody Report report) {
        if (jwtTokenProvider.getUsername(token).equals(report.getRecipientUsername())) {
            reportService.remove(report);
        } else {
            throw new AccessDeniedException("You cannot edit someone else's account");
        }
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('TEACHER')")
    public ResponseEntity<?> getReport(@RequestHeader("Authorization") String token) {
        Report report = reportService.findByRecipientUsername(jwtTokenProvider.getUsername(token));
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('TEACHER')")
    public ResponseEntity<?> update(@RequestHeader("Authorization") String token, @RequestBody Report report) {
        if (jwtTokenProvider.getUsername(token).equals(report.getRecipientUsername())) {
            return new ResponseEntity<>(reportService.update(report), HttpStatus.OK);
        } else {
            throw new AccessDeniedException("You cannot edit someone else's account");
        }
    }
}
