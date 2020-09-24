package com.yuditsky.classroom.service;

import com.yuditsky.classroom.model.User;

import java.util.List;

public interface UserService {
    User create(User user);

    User findOrCreateByUsername(String username);

    User findByUsername(String username);

    void update(User user);

    User changeHandState(User user);

    User logIn(String username);

    void logOut(User user);

    List<User> getAuthorizedUsers();
}
