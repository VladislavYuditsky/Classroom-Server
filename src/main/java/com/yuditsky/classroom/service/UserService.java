package com.yuditsky.classroom.service;

import com.yuditsky.classroom.model.Role;
import com.yuditsky.classroom.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    User create(User user);

    User findByUsernameOrCreate(String username, Set<Role> roles);

    User findByUsername(String username);

    void update(User user);

    User changeHandState(User user);

    User logIn(String username, Set<Role> roles);

    void logOut(String username);

    List<User> getAuthorizedUsers();
}
