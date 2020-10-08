package com.yuditsky.classroom.service.impl;

import com.yuditsky.classroom.converter.ReportDtoToEntityConverter;
import com.yuditsky.classroom.converter.ReportEntityToDtoConverter;
import com.yuditsky.classroom.converter.UserDtoToEntityConverter;
import com.yuditsky.classroom.entity.ReportEntity;
import com.yuditsky.classroom.entity.UserEntity;
import com.yuditsky.classroom.exception.EntityNotFoundException;
import com.yuditsky.classroom.model.Report;
import com.yuditsky.classroom.repository.ReportRepository;
import com.yuditsky.classroom.service.ReportService;
import com.yuditsky.classroom.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final ReportDtoToEntityConverter reportDtoToEntityConverter;
    private final ReportEntityToDtoConverter reportEntityToDtoConverter;
    private final UserService userService;
    private final UserDtoToEntityConverter userDtoToEntityConverter;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository,
                             ReportDtoToEntityConverter reportDtoToEntityConverter,
                             ReportEntityToDtoConverter reportEntityToDtoConverter,
                             UserService userService,
                             UserDtoToEntityConverter userDtoToEntityConverter
    ) {
        this.reportRepository = reportRepository;
        this.reportDtoToEntityConverter = reportDtoToEntityConverter;
        this.reportEntityToDtoConverter = reportEntityToDtoConverter;
        this.userService = userService;
        this.userDtoToEntityConverter = userDtoToEntityConverter;
    }

    @Override
    public Report save(Report report) {
        ReportEntity reportEntity = reportDtoToEntityConverter.convert(report);
        UserEntity recipient = userDtoToEntityConverter
                .convert(userService.findByUsername(report.getRecipientUsername()));
        reportEntity.setRecipient(recipient);
        log.debug("Saving reportEntity: {}", reportEntity);
        return reportEntityToDtoConverter.convert(reportRepository.save(reportEntity));
    }

    @Override
    public List<Report> findByReportingDate(LocalDate date) {
        return reportRepository.findByReportingDate(date)
                .stream()
                .map(reportEntityToDtoConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public Report findByRecipientUsername(String username) {
        return reportRepository.findByRecipientUsername(username).map(reportEntityToDtoConverter::convert)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("Report with recipient username {0} not found", username);
                });
    }

    @Override
    public Report findById(Long id) {
        return reportRepository.findById(id).map(reportEntityToDtoConverter::convert)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("Report with id {0} not found", id);
                });
    }

    @Override
    public Report update(Report report) {
        log.debug("Update {}", report);
        ReportEntity dbReportEntity = reportRepository.save(reportDtoToEntityConverter.convert(report));
        return reportEntityToDtoConverter.convert(dbReportEntity);
    }

    @Override
    public void remove(Report report) {
        log.debug("Remove report with id {}", report.getId());
        reportRepository.deleteById(report.getId());
    }
}
