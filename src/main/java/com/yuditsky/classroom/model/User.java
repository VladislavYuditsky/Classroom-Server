package com.yuditsky.classroom.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    private String username;
    private boolean isHandUp;
    private String email;
    private boolean authorized;
    private Set<Role> roles;
}
