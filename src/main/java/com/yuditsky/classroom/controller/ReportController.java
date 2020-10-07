package com.yuditsky.classroom.controller;

import com.yuditsky.classroom.model.Report;
import com.yuditsky.classroom.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("report")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping()
    public ResponseEntity<?> saveReport(@RequestBody Report report) {
        return new ResponseEntity<>(reportService.save(report), HttpStatus.OK);
    }

    @PostMapping("update")
    public ResponseEntity<?> updateReport(@RequestBody Report report) {
        Report dbReport = reportService.findById(report.getId());
        dbReport.setGenerationFrequency(report.getGenerationFrequency());
        return new ResponseEntity<>(reportService.update(dbReport), HttpStatus.OK);
    }

    @PostMapping("remove")
    public ResponseEntity<?> removeReport(@RequestBody Report report) {
        reportService.remove(report);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("{username}")
    public ResponseEntity<?> getReport(@PathVariable("username") String username) {
        return new ResponseEntity<>(reportService.findByRecipientUsername(username), HttpStatus.OK);
    }
}
