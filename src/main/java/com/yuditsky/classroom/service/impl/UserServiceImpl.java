package com.yuditsky.classroom.service.impl;

import com.yuditsky.classroom.converter.UserDtoToEntityConverter;
import com.yuditsky.classroom.converter.UserEntityToDtoConverter;
import com.yuditsky.classroom.entity.UserEntity;
import com.yuditsky.classroom.exception.*;
import com.yuditsky.classroom.model.Action;
import com.yuditsky.classroom.model.Role;
import com.yuditsky.classroom.model.User;
import com.yuditsky.classroom.repository.UserRepository;
import com.yuditsky.classroom.service.LoggerService;
import com.yuditsky.classroom.service.UserService;
import com.yuditsky.classroom.validator.impl.UserValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final UserDtoToEntityConverter userDtoToEntityConverter;
    private final UserEntityToDtoConverter userEntityToDtoConverter;
    private final LoggerService logger;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            UserValidator userValidator,
            UserDtoToEntityConverter userDtoToEntityConverter,
            UserEntityToDtoConverter userEntityToDtoConverter,
            LoggerService logger
    ) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.userDtoToEntityConverter = userDtoToEntityConverter;
        this.userEntityToDtoConverter = userEntityToDtoConverter;
        this.logger = logger;
    }

    @Override
    public User create(User user) {
        userRepository.findByUsername(user.getUsername()).ifPresent((userEntity -> {
            throw new AlreadyExistedException("User with username {0} is already existing", userEntity.getUsername());
        }));
        userValidator.validate(user);
        UserEntity userEntity = userDtoToEntityConverter.convert(user);
        log.debug("Saving userEntity: {}", userEntity);
        return userEntityToDtoConverter.convert(userRepository.save(userEntity));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).map(userEntityToDtoConverter::convert).orElseThrow(() -> {
            throw new EntityNotFoundException("User with username {0} not found", username);
        });
    }

    @Override
    public User update(User user) {
        userValidator.validate(user);
        log.debug("Update {}", user);
        UserEntity dbUserEntity = userRepository.save(userDtoToEntityConverter.convert(user));
        return userEntityToDtoConverter.convert(dbUserEntity);
    }

    @Override
    public User changeHandState(User user) {
        user = findByUsername(user.getUsername());
        if (!user.getRoles().contains(Role.TEACHER) && user.isAuthorized()) {
            user.setHandUp(!user.isHandUp());
            update(user);
            logger.log(user.getUsername(), user.isHandUp() ? Action.HAND_UP : Action.HAND_DOWN);
        } else {
            throw new AccessDeniedException("Access denied");
        }
        return user;
    }

    @Override
    public User logIn(User user) {
        User userDb = userRepository.findByUsername(user.getUsername()).map(userEntityToDtoConverter::convert)
                .orElseGet(() -> create(user));
        if (user.getRoles().equals(userDb.getRoles())) {
            if (!userDb.isAuthorized()) {
                userDb.setAuthorized(true);
                update(userDb);
                if (userDb.getRoles().contains(Role.STUDENT)) {
                    logger.log(userDb.getUsername(), Action.LOG_IN);
                }
            } else {
                throw new AlreadyAuthorizedException("User with username {0} is already authorized", user.getUsername());
            }
        } else {
            throw new AccessDeniedException("User with the same name already exists with the {0} role",
                    userDb.getRoles().iterator().next().toString().toLowerCase());
        }

        return userDb;
    }

    @Override
    public void logOut(String username) {
        User user = findByUsername(username);
        if (user.isAuthorized()) {
            user.setAuthorized(false);
            if (user.getRoles().contains(Role.STUDENT)) {
                user.setHandUp(false);
                logger.log(username, Action.HAND_DOWN);
                logger.log(username, Action.LOG_OUT);
            }
            update(user);
        } else {
            throw new AccessDeniedException("User with username {0} is not authorized", username);
        }
    }

    @Override
    public List<User> findAuthorizedUsers() {
        return userRepository.findUserEntitiesByAuthorized(true)
                .stream()
                .map(userEntityToDtoConverter::convert)
                .sorted(Comparator.comparing(User::getUsername))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findByRole(Role role) {
        return userRepository.findByRoles(role)
                .stream()
                .map(userEntityToDtoConverter::convert)
                .sorted(Comparator.comparing(User::getUsername))
                .collect(Collectors.toList());
    }

    @Override
    public User changeEmail(User user) {
        String newEmail = user.getEmail();
        userValidator.validateEmail(newEmail);

        boolean isBusy = userRepository.findByEmail(newEmail).map(userEntityToDtoConverter::convert).isPresent();
        if (!isBusy) {
            User userDb = findByUsername(user.getUsername());
            userDb.setEmail(newEmail);
            return update(userDb);
        } else {
            throw new EmailBusyException("{0} is busy", user.getEmail());
        }
    }
}
