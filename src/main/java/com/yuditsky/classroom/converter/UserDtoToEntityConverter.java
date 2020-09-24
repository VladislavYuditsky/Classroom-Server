package com.yuditsky.classroom.converter;

import com.yuditsky.classroom.entity.UserEntity;
import com.yuditsky.classroom.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UserDtoToEntityConverter implements Converter<User, UserEntity> {
    @Override
    public UserEntity convert(User user) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        return userEntity;
    }
}
