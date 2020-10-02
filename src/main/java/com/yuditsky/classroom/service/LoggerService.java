package com.yuditsky.classroom.service;

import com.yuditsky.classroom.model.Logger;

public interface LoggerService {
    void log(String username, String action);

    Logger findByUsername(String username);
}
