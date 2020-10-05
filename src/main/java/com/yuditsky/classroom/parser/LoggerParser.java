package com.yuditsky.classroom.parser;

import com.yuditsky.classroom.model.Logger;

import java.util.List;

public class LoggerParser {
    public static String toString(List<Logger> logs) {
        StringBuilder result = new StringBuilder();

        for (Logger log : logs) {
            result
                    .append(log.getUsername())
                    .append(" ")
                    .append(log.getAction().toString())
                    .append(" ")
                    .append(log.getDateTime())
                    .append("\n");
        }

        return result.toString();
    }
}
