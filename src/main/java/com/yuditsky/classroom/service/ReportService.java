package com.yuditsky.classroom.service;

import com.yuditsky.classroom.model.Report;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    Report save(Report report);

    List<Report> findByReportingDate(LocalDate date);
}
