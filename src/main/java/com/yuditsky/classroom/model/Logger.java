package com.yuditsky.classroom.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Logger {
    private Long id;
    private String username;
    private Action action;
    private LocalDateTime dateTime;
}
