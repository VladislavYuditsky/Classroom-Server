package com.yuditsky.classroom.converter;

import com.yuditsky.classroom.entity.LoggerEntity;
import com.yuditsky.classroom.model.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class LoggerEntityToDtoConverter implements Converter<LoggerEntity, Logger> {
    @Override
    public Logger convert(LoggerEntity loggerEntity) {
        Logger logger = new Logger();
        BeanUtils.copyProperties(loggerEntity, logger);
        return logger;
    }
}
