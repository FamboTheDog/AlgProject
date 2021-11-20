package com.example.backend.db;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "name is required")
    @NotNull(message = "name can't be null")
    @Column(name = "name")
    private String name;

    @Size(min=8, message = "minimum password length is 8 characters")
    @NotBlank(message = "password is required")
    @NotNull(message = "password can't be null")
    @Column(name = "password")
    private String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
