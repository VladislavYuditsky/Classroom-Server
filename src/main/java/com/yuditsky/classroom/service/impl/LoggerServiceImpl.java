package com.yuditsky.classroom.service.impl;

import com.yuditsky.classroom.converter.LoggerEntityToDtoConverter;
import com.yuditsky.classroom.entity.LoggerEntity;
import com.yuditsky.classroom.model.Action;
import com.yuditsky.classroom.model.Logger;
import com.yuditsky.classroom.repository.LoggerRepository;
import com.yuditsky.classroom.service.LoggerService;
import com.yuditsky.classroom.specification.LoggerSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class LoggerServiceImpl implements LoggerService {
    private final LoggerRepository loggerRepository;
    private final LoggerEntityToDtoConverter loggerEntityToDtoConverter;

    @Autowired
    public LoggerServiceImpl(LoggerRepository loggerRepository, LoggerEntityToDtoConverter loggerEntityToDtoConverter) {
        this.loggerRepository = loggerRepository;
        this.loggerEntityToDtoConverter = loggerEntityToDtoConverter;
    }

    @Transactional
    @Override
    public void log(String username, Action action) {
        loggerRepository.save(LoggerEntity.builder()
                .username(username)
                .action(action)
                .dateTime(LocalDateTime.now(ZoneId.of("UTC-0")))
                .build());
    }

    @Override
    public List<Logger> findByUsernameWithFilter(String username, String filter) {
        filter = "username:" + username + "," + filter;

        return findAllWithFilter(filter);
    }

    @Override
    public List<Logger> findAllWithFilter(String filter) {
        Specification<LoggerEntity> specification = filterToSpecification(filter);

        return loggerRepository.findAll(specification)
                .stream()
                .map(loggerEntityToDtoConverter::convert)
                .sorted(Comparator.comparing(Logger::getDateTime))
                .collect(Collectors.toList());
    }

    @Override
    public Long countAllWithFilter(String filter) {
        Specification<LoggerEntity> specification = filterToSpecification(filter);
        return loggerRepository.count(specification);
    }

    @Override
    public List<String> findUsernamesByDate(LocalDateTime date) {
        return loggerRepository.findDistinctUsernameByDateTimeAfter(date);
    }

    private Specification<LoggerEntity> filterToSpecification(String filter) {
        LoggerSpecificationBuilder builder = new LoggerSpecificationBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)([\\w -:]+?),", Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(filter + ",");

        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        return builder.build();
    }
}
