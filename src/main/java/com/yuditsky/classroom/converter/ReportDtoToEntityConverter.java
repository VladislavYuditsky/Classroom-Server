package com.yuditsky.classroom.converter;

import com.yuditsky.classroom.entity.ReportEntity;
import com.yuditsky.classroom.entity.UserEntity;
import com.yuditsky.classroom.exception.EntityNotFoundException;
import com.yuditsky.classroom.model.Report;
import com.yuditsky.classroom.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ReportDtoToEntityConverter implements Converter<Report, ReportEntity> {

    private final UserRepository userRepository;

    @Autowired
    public ReportDtoToEntityConverter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ReportEntity convert(Report report) {
        ReportEntity reportEntity = new ReportEntity();
        BeanUtils.copyProperties(report, reportEntity);
        reportEntity.setReportingDate(LocalDate.now().plusDays(reportEntity.getGenerationFrequency()));
        UserEntity recipient = userRepository.findByUsername(report.getRecipientUsername()).orElseThrow(() -> {
            throw new EntityNotFoundException("User with username {0} not found", report.getRecipientUsername());
        });
        reportEntity.setRecipient(recipient);
        return reportEntity;
    }
}
