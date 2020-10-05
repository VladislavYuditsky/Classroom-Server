package com.yuditsky.classroom.converter;

import com.yuditsky.classroom.entity.ReportEntity;
import com.yuditsky.classroom.model.Report;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ReportEntityToDtoConverter implements Converter<ReportEntity, Report> {
    @Override
    public Report convert(ReportEntity reportEntity) {
        Report report = new Report();
        BeanUtils.copyProperties(reportEntity, report, "reportingDate");
        return report;
    }
}
