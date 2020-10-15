package com.yuditsky.classroom.service;

import com.yuditsky.classroom.model.Role;
import com.yuditsky.classroom.model.User;

import java.util.List;

public interface UserService {
    User create(User user);

    User findByUsername(String username);

    User update(User user);

    User changeHandState(String username);

    User logIn(User user);

    void logOut(String username);

    List<User> findAuthorizedUsers();

    List<User> findByRole(Role role);

    User changeEmail(String username, String email);
}
