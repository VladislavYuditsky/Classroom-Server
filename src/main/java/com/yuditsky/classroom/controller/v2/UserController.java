package com.yuditsky.classroom.controller.v2;

import com.yuditsky.classroom.model.Logger;
import com.yuditsky.classroom.model.Role;
import com.yuditsky.classroom.model.User;
import com.yuditsky.classroom.security.JwtTokenProvider;
import com.yuditsky.classroom.service.LoggerService;
import com.yuditsky.classroom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v2/user")
public class UserController {

    private final UserService userService;
    private final LoggerService loggerService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserController(
            UserService userService,
            LoggerService loggerService,
            JwtTokenProvider jwtTokenProvider
    ) {
        this.userService = userService;
        this.loggerService = loggerService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PatchMapping("update/hand-state")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<?> updateHandState(@RequestHeader("Authorization") String token) {
        return new ResponseEntity<>(userService.changeHandState(jwtTokenProvider.getUsername(token)), HttpStatus.OK);
    }

    @PatchMapping("update/email")
    @PreAuthorize("hasAuthority('TEACHER')")
    public ResponseEntity<?> updateEmail(@RequestHeader("Authorization") String token, @RequestBody User user) {
        String username = jwtTokenProvider.getUsername(token);
        String email = user.getEmail();
        return new ResponseEntity<>(userService.changeEmail(username, email), HttpStatus.OK);
    }

    @GetMapping("authorized")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getAuthorizedUsers() {
        List<User> users = userService.findAuthorizedUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("students")
    @PreAuthorize("hasAuthority('TEACHER')")
    public ResponseEntity<?> getStudents() {
        List<User> students = userService.findByRole(Role.STUDENT);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("student/{username}")
    @PreAuthorize("hasAuthority('TEACHER')")
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
