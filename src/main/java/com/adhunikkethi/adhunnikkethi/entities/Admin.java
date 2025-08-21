package com.adhunikkethi.adhunnikkethi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false)
    private String password;  // Store hashed passwords

    @Enumerated(EnumType.STRING)
    @Column(name = "access_level", nullable = false)
    private AccessLevel accessLevel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    public enum AccessLevel {
        SUPER_ADMIN,
        MODERATOR,
        SUPPORT
    }

    public enum Status {
        ACTIVE,
        INACTIVE,
        SUSPENDED
    }
}
