package com.yuditsky.classroom.converter;

import com.yuditsky.classroom.entity.LoggerEntity;
import com.yuditsky.classroom.model.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class LoggerEntityToDtoConverter implements Converter<LoggerEntity, Logger> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Override
    public Logger convert(LoggerEntity loggerEntity) {
        Logger logger = new Logger();
        BeanUtils.copyProperties(loggerEntity, logger, "dateTime");
        logger.setDateTime(loggerEntity.getDateTime().format(formatter));
        return logger;
    }
}
