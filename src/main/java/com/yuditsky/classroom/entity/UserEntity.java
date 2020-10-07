package com.yuditsky.classroom.entity;

import com.yuditsky.classroom.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private boolean isHandUp;

    @Column
    private String email;

    @Column
    private boolean authorized;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<Role> roles;

    @OneToOne(mappedBy = "recipient", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private ReportEntity reportEntity;
}
