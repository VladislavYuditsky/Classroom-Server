package com.yuditsky.classroom.service.impl;

import com.yuditsky.classroom.converter.ReportDtoToEntityConverter;
import com.yuditsky.classroom.converter.ReportEntityToDtoConverter;
import com.yuditsky.classroom.entity.ReportEntity;
import com.yuditsky.classroom.model.Report;
import com.yuditsky.classroom.repository.ReportRepository;
import com.yuditsky.classroom.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final ReportDtoToEntityConverter reportDtoToEntityConverter;
    private final ReportEntityToDtoConverter reportEntityToDtoConverter;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository,
                             ReportDtoToEntityConverter reportDtoToEntityConverter,
                             ReportEntityToDtoConverter reportEntityToDtoConverter
    ) {
        this.reportRepository = reportRepository;
        this.reportDtoToEntityConverter = reportDtoToEntityConverter;
        this.reportEntityToDtoConverter = reportEntityToDtoConverter;
    }

    @Override
    public Report save(Report report) {
        ReportEntity reportEntity = reportDtoToEntityConverter.convert(report);
        return reportEntityToDtoConverter.convert(reportRepository.save(reportEntity));
    }

    @Override
    public List<Report> findByReportingDate(LocalDate date) {
        return reportRepository.findByReportingDate(date)
                .stream()
                .map(reportEntityToDtoConverter::convert)
                .collect(Collectors.toList());
    }
}
