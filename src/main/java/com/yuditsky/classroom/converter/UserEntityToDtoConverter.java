package com.yuditsky.classroom.converter;

import com.yuditsky.classroom.entity.UserEntity;
import com.yuditsky.classroom.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UserEntityToDtoConverter implements Converter<UserEntity, User> {
    @Override
    public User convert(UserEntity userEntity) {
        User user = new User();
        BeanUtils.copyProperties(userEntity, user);
        return user;
    }
}
