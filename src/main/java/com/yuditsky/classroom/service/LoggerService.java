package com.yuditsky.classroom.service;

import com.yuditsky.classroom.model.Action;
import com.yuditsky.classroom.model.Logger;

public interface LoggerService {
    void log(String username, Action action);

    Logger findByUsername(String username);
}
