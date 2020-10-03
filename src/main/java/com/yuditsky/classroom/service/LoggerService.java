package com.yuditsky.classroom.service;

import com.yuditsky.classroom.model.Action;
import com.yuditsky.classroom.model.Logger;

import java.util.List;

public interface LoggerService {
    void log(String username, Action action);

    List<Logger> findByUsername(String username);
}
