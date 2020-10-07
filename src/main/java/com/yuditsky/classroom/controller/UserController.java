package com.yuditsky.classroom.controller;

import com.yuditsky.classroom.model.Logger;
import com.yuditsky.classroom.model.Report;
import com.yuditsky.classroom.model.Role;
import com.yuditsky.classroom.model.User;
import com.yuditsky.classroom.service.LoggerService;
import com.yuditsky.classroom.service.ReportService;
import com.yuditsky.classroom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;
    private final LoggerService loggerService;

    @Autowired
    public UserController(UserService userService, LoggerService loggerService) {
        this.userService = userService;
        this.loggerService = loggerService;
    }

    @PostMapping("signIn")
    public ResponseEntity<?> signIn(@RequestBody User user) {
        return new ResponseEntity<>(userService.logIn(user), HttpStatus.OK);
    }

    @PostMapping("handAction")
    public ResponseEntity<?> changeHandState(@RequestBody User user) {
        user = userService.changeHandState(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("signOut")
    public ResponseEntity<?> signOut(@RequestBody User user) {
        userService.logOut(user.getUsername());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("user/update")
    public ResponseEntity<?> updateEmail(@RequestBody User user) {
        User dbUser = userService.findByUsername(user.getUsername());
        dbUser.setEmail(user.getEmail());
        return new ResponseEntity<>(userService.update(dbUser), HttpStatus.OK);
    }

    @GetMapping("users")
    public ResponseEntity<?> getAuthorizedUsers() {
        List<User> users = userService.findAuthorizedUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("students")
    public ResponseEntity<?> getStudents() {
        List<User> students = userService.findByRole(Role.STUDENT);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("student/{username}")
    public ResponseEntity<?> getStudentActions(@PathVariable("username") String username,
                                               @RequestParam(value = "search") String filter) {
        List<Logger> logs = loggerService.findByUsernameWithFilter(username, filter);
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }

    @SendTo("/topic/users")
    @MessageMapping("updateState")
    public List<User> updateState() {
        return userService.findAuthorizedUsers();
    }
}
