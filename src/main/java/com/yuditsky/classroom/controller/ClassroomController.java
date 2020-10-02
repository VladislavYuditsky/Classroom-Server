package com.yuditsky.classroom.controller;

import com.yuditsky.classroom.model.Role;
import com.yuditsky.classroom.model.User;
import com.yuditsky.classroom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClassroomController {

    private final UserService userService;

    @Autowired
    public ClassroomController(UserService userService) {
        this.userService = userService;
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

    @SendTo("/topic/users")
    @MessageMapping("updateState")
    public List<User> updateState() {
        return userService.findAuthorizedUsers();
    }
}
