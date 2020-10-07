package com.yuditsky.classroom.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    private Long id;
    private String recipientUsername;
    private Long generationFrequency;
}
