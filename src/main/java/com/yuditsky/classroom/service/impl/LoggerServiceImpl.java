package com.yuditsky.classroom.service.impl;

import com.yuditsky.classroom.converter.LoggerEntityToDtoConverter;
import com.yuditsky.classroom.entity.LoggerEntity;
import com.yuditsky.classroom.model.Action;
import com.yuditsky.classroom.model.Logger;
import com.yuditsky.classroom.repository.LoggerRepository;
import com.yuditsky.classroom.service.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
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

    @Override
    public void log(String username, Action action) {
        loggerRepository.save(LoggerEntity.builder()
                .username(username)
                .action(action)
                .dateTime(LocalDateTime.now())
                .build());
    }

    @Override
    public List<Logger> findByUsername(String username) {
        return loggerRepository.findByUsername(username)
                .stream()
                .map(loggerEntityToDtoConverter::convert)
                .sorted(Comparator.comparing(Logger::getDateTime))
                .collect(Collectors.toList());
    }
}
