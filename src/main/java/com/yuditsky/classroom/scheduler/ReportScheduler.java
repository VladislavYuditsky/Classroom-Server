package com.yuditsky.classroom.scheduler;

import com.yuditsky.classroom.model.Action;
import com.yuditsky.classroom.model.Report;
import com.yuditsky.classroom.sender.MailSender;
import com.yuditsky.classroom.service.LoggerService;
import com.yuditsky.classroom.service.ReportService;
import com.yuditsky.classroom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
        List<Report> reports = reportService.findByReportingDate(LocalDate.now());

        for (Report report : reports) {
            String message = generateMessage(LocalDateTime.now().minusDays(report.getGenerationFrequency()));
            String email = userService.findByUsername(report.getRecipientUsername()).getEmail();
            if (!StringUtils.isEmpty(email)) {
                mailSender.send(email, subject, message);
            }
            reportService.update(report);
        }
    }

    private String generateMessage(LocalDateTime dateFrom) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimePattern);

        List<String> usernames = loggerService.findUsernamesByDate(dateFrom);

        StringBuilder message = new StringBuilder("Report for the period from " + dateFrom.format(formatter) + " to "
                + LocalDateTime.now().format(formatter) + "\n\n");

        for (String username : usernames) {
            String usernameWithDateFilter = "username:" + username + "," + "dateTime>" + dateFrom.format(formatter) + ",";

            message
                    .append(username)
                    .append(" - ");

            for (Action action : Action.values()) {
                String actionFilter = usernameWithDateFilter + "action:" + action.toString() + ",";
                Long actionCount = loggerService.countAllWithFilter(actionFilter);
                message
                        .append(" ")
                        .append(action.toString())
                        .append(": ")
                        .append(actionCount)
                        .append(" / ");
            }
            message.append("\n");
        }

        return message.toString();
    }
}
