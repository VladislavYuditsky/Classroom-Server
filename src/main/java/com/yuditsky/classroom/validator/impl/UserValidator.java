package com.yuditsky.classroom.validator.impl;

import com.yuditsky.classroom.exception.InvalidEntityException;
import com.yuditsky.classroom.model.User;
import com.yuditsky.classroom.validator.EntityValidator;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidator implements EntityValidator<User> {
    private Pattern emailPattern = Pattern.compile("^\\S+@\\S+.\\S+${3,127}$");
    private Pattern usernamePattern = Pattern.compile("^[a-zA-Z1-9]{3,10}$");

    @Override
    public void validate(User user) {
        validateUsername(user.getUsername());
    }

    public void validateEmail(String email) {
        if (!isValidEmail(email)) {
            throw new InvalidEntityException("Invalid email {0}", email);
        }
    }

    private void validateUsername(String username) {
        if (!isValidUsername(username)) {
            throw new InvalidEntityException("Invalid username {}", username);
        }
    }

    private boolean isValidEmail(String email) {
        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidUsername(String username) {
        Matcher matcher = usernamePattern.matcher(username);
        return matcher.matches();
    }
}
