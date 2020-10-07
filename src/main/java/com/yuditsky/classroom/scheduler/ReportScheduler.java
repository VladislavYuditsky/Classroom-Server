package com.yuditsky.classroom.scheduler;

import com.yuditsky.classroom.model.Logger;
import com.yuditsky.classroom.model.Report;
import com.yuditsky.classroom.parser.LoggerParser;
import com.yuditsky.classroom.sender.MailSender;
import com.yuditsky.classroom.service.LoggerService;
import com.yuditsky.classroom.service.ReportService;
import com.yuditsky.classroom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.yuditsky.classroom.constant.Constants.dateTimePattern;

@Component
public class ReportScheduler {
    private static final String subject = "Report";

    private final MailSender mailSender;
    private final ReportService reportService;
    private final LoggerService loggerService;
    private final UserService userService;

    @Autowired
    public ReportScheduler(
            MailSender mailSender,
            ReportService reportService,
            LoggerService loggerService,
            UserService userService
    ) {
        this.mailSender = mailSender;
        this.reportService = reportService;
        this.loggerService = loggerService;
        this.userService = userService;
    }

    @Scheduled(cron = "${cron.expression}")
    public void sendReports() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimePattern);

        List<Report> reports = reportService.findByReportingDate(LocalDate.now());

        for (Report report : reports) {
            String filter = "dateTime>" + LocalDateTime.now().minusDays(report.getGenerationFrequency()).format(formatter);
            List<Logger> logs = loggerService.findAllWithFilter(filter);
            String message = LoggerParser.toString(logs);
            String email = userService.findByUsername(report.getRecipientUsername()).getEmail();
            mailSender.send(email, subject, message);
        }
    }
}
