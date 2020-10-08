package com.yuditsky.classroom.validator.impl;

import com.yuditsky.classroom.exception.InvalidEntityException;
import com.yuditsky.classroom.model.User;
import com.yuditsky.classroom.validator.EntityValidator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
        if (!isValidString(emailPattern, email)) {
            throw new InvalidEntityException("Invalid email {0}", email);
        }
    }

    private void validateUsername(String username) {
        if (!isValidString(usernamePattern, username)) {
            throw new InvalidEntityException("Invalid username {0}", username);
        }
    }

    private boolean isValidString(Pattern pattern, String string){
        if(!StringUtils.isEmpty(string)){
            Matcher matcher = pattern.matcher(string);
            return matcher.matches();
        } else {
            return false;
        }
    }
}
