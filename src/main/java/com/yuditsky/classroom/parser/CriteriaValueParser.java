package com.yuditsky.classroom.parser;

import com.yuditsky.classroom.exception.InvalidDateTimeFormatException;
import com.yuditsky.classroom.model.Action;
import com.yuditsky.classroom.specification.SearchCriteria;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static com.yuditsky.classroom.constant.Constants.dateTimePattern;

public class CriteriaValueParser {
    public static Action toAction(SearchCriteria criteria) {
        return Action.valueOf(criteria.getValue().toString());
    }

    public static LocalDateTime toLocalDateTime(SearchCriteria criteria) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimePattern);
        try {
            return LocalDateTime.parse(criteria.getValue().toString(), formatter);
        } catch (DateTimeParseException e) {
            throw new InvalidDateTimeFormatException("{0} doesn't match pattern {1} .", criteria.getValue().toString(), dateTimePattern);
        }
    }
}
