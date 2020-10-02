package com.yuditsky.classroom.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    private String username;
    Set<Role> roles;
}
