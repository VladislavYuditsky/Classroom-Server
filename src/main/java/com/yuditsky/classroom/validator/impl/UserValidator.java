package com.yuditsky.classroom.validator.impl;

import com.yuditsky.classroom.exception.InvalidEntityException;
import com.yuditsky.classroom.model.User;
import com.yuditsky.classroom.validator.EntityValidator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class UserValidator implements EntityValidator<User> {

    @Override
    public void validate(User user) {
        if (!isValid(user)) {
            throw new InvalidEntityException("Invalid user {0}", user);
        }
    }

    private boolean isValid(User user) {
        String username = user.getUsername();
        return !StringUtils.isEmpty(username);
    }
}
