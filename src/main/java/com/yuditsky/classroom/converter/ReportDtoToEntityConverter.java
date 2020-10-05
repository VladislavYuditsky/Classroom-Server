package com.yuditsky.classroom.converter;

import com.yuditsky.classroom.entity.ReportEntity;
import com.yuditsky.classroom.model.Report;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ReportDtoToEntityConverter implements Converter<Report, ReportEntity> {

    @Override
    public ReportEntity convert(Report report) {
        ReportEntity reportEntity = new ReportEntity();
        BeanUtils.copyProperties(report, reportEntity);
        reportEntity.setReportingDate(LocalDate.now().plusDays(reportEntity.getGenerationFrequency()));
        return reportEntity;
    }
}
