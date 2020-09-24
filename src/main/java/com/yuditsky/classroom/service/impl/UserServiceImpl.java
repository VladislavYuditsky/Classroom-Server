package com.yuditsky.classroom.service.impl;

import com.yuditsky.classroom.converter.UserDtoToEntityConverter;
import com.yuditsky.classroom.converter.UserEntityToDtoConverter;
import com.yuditsky.classroom.entity.UserEntity;
import com.yuditsky.classroom.exception.AccessDeniedException;
import com.yuditsky.classroom.exception.AlreadyAuthorizedException;
import com.yuditsky.classroom.exception.AlreadyExistedException;
import com.yuditsky.classroom.exception.EntityNotFoundException;
import com.yuditsky.classroom.model.User;
import com.yuditsky.classroom.repository.UserRepository;
import com.yuditsky.classroom.service.UserService;
import com.yuditsky.classroom.validator.impl.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final UserDtoToEntityConverter userDtoToEntityConverter;
    private final UserEntityToDtoConverter userEntityToDtoConverter;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            UserValidator userValidator,
            UserDtoToEntityConverter userDtoToEntityConverter,
            UserEntityToDtoConverter userEntityToDtoConverter
    ) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.userDtoToEntityConverter = userDtoToEntityConverter;
        this.userEntityToDtoConverter = userEntityToDtoConverter;
    }

    @Override
    public User create(User user) {
        userRepository.findByUsername(user.getUsername()).ifPresent((userEntity -> {
            throw new AlreadyExistedException("User with username {0} is already existing", userEntity.getUsername());
        }));
        userValidator.validate(user);
        UserEntity userEntity = userDtoToEntityConverter.convert(user);
        return userEntityToDtoConverter.convert(userRepository.save(userEntity));
    }

    @Override
    public User findOrCreateByUsername(String username) {
        return userRepository.findByUsername(username).map(userEntityToDtoConverter::convert)
                .orElseGet(() ->
                        create(User.builder()
                                .username(username)
                                .isHandUp(false)
                                .authorized(false)
                                .build()));

    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).map(userEntityToDtoConverter::convert).orElseThrow(() -> {
            throw new EntityNotFoundException("User with username {0} not found", username);
        });
    }

    @Override
    public void update(User user) {
        userValidator.validate(user);
        userRepository.save(userDtoToEntityConverter.convert(user));
    }

    @Override
    public User changeHandState(User user) {
        user = findByUsername(user.getUsername());
        if (user.isAuthorized()) {
            user.setHandUp(!user.isHandUp());
            update(user);
        } else {
            throw new AccessDeniedException("User with username {0} is not authorized", user.getUsername());
        }
        return user;
    }

    @Override
    public User logIn(String username) {
        User user = findOrCreateByUsername(username);
        if (!user.isAuthorized()) {
            user.setAuthorized(true);
            update(user);
        } else {
            throw new AlreadyAuthorizedException("User with username {0} is already authorized", username);
        }

        return user;
    }

    @Override
    public void logOut(User user) {
        user = findByUsername(user.getUsername());
        if (user.isAuthorized()) {
            user.setAuthorized(false);
            user.setHandUp(false);
            update(user);
        } else {
            throw new AccessDeniedException("User with username {0} is not authorized", user.getUsername());
        }
    }

    @Override
    public List<User> getAuthorizedUsers() {
        return userRepository.findUserEntitiesByAuthorized(true)
                .stream()
                .map(userEntityToDtoConverter::convert)
                .sorted(Comparator.comparing(User::getId))
                .collect(Collectors.toList());
    }
}
