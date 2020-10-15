package com.yuditsky.classroom.controller.v2;

import com.yuditsky.classroom.model.AuthenticationRequest;
import com.yuditsky.classroom.model.AuthenticationResponse;
import com.yuditsky.classroom.model.User;
import com.yuditsky.classroom.security.JwtTokenProvider;
import com.yuditsky.classroom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api/v2/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthenticationController(
            AuthenticationManager authenticationManager,
            UserService userService,
            JwtTokenProvider jwtTokenProvider
    ) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        User user = User.builder().username(request.getUsername()).roles(request.getRoles()).build();
        user = userService.logIn(user);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),
                request.getPassword()));
        String token = jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
        return ResponseEntity.ok(AuthenticationResponse.builder().user(user).token(token).build());
    }

    @PostMapping("logout")
    @PreAuthorize("isAuthenticated()")
    public void logout(@RequestHeader("Authorization") String token, HttpServletRequest request, HttpServletResponse response) {
        userService.logOut(jwtTokenProvider.getUsername(token));
    }
}
 