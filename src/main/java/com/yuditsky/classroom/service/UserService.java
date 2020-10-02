package com.yuditsky.classroom.service;

import com.yuditsky.classroom.model.User;

import java.util.List;

public interface UserService {
    User create(User user);

    User findByUsername(String username);

    void update(User user);

    User changeHandState(User user);

    User logIn(User user);

    void logOut(String username);

    List<User> getAuthorizedUsers();
}
